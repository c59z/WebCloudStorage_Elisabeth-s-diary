package com.yuki.Shiro;

import cn.hutool.core.bean.BeanUtil;
import com.yuki.Domain.Entity.User;
import com.yuki.Service.UserService;
import com.yuki.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        // 给资源进行授权
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        // 拿到当前登录用户
//        Subject subject = SecurityUtils.getSubject();
//        System.out.println(subject.getPrincipal());
//        AccountProfile user = (AccountProfile)subject.getPrincipal();
//        List<String> perms = userService.getUserPermsById(user.getId());
//        // 设置当前用户的权限，从数据库查询而来
//        // 添加权限
//        for (String perm : perms) {
//            info.addStringPermission(perm);
//        }
//        System.out.println("权限如下:>>>");
//        System.out.println(info.getStringPermissions());
//        return info;
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;
        System.out.println("jwt ");
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        User user = userService.getUserById(Long.valueOf(userId));
        if(user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定！");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
//        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}