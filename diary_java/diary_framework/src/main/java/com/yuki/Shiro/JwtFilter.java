package com.yuki.Shiro;

import cn.hutool.json.JSONUtil;
import com.yuki.Shiro.JwtToken;
import com.yuki.Domain.ResponseResult;
import com.yuki.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 获取jwt
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("token");

        System.out.println("token获取:>>> "+jwt);
        // 如果用户没有登录
        if(ObjectUtils.isEmpty(jwt)){
            return null;
        }

        return new JwtToken(jwt);
    }

    // 对跨域进行支持
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 判断token是否正常(没有过期)
        // 获取jwt
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("token");
        // 如果用户没有登录
        if(ObjectUtils.isEmpty(jwt)){
            // 没有jwt
            System.out.println("用户没有登录");
            return true;
        }else{
            // 有jwt
            // 获取并验证jwt
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if(claim == null || jwtUtils.isTokenExpired(claim.getExpiration())){
                throw new ExpiredCredentialsException("token已失效,请重新登录");
            }
            //进入登录步骤
            return  executeLogin(servletRequest, servletResponse);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        ResponseResult result = ResponseResult.fail(throwable.getMessage());
        String json = JSONUtil.toJsonStr(result);

        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}