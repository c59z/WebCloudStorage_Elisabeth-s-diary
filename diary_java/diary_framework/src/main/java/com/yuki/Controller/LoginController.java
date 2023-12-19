package com.yuki.Controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.yuki.Domain.ResponseResult;
import com.yuki.Domain.Entity.User;
import com.yuki.Domain.Dto.UserDto;
import com.yuki.Domain.Vo.UserVo;
import com.yuki.Service.impl.UserServiceImpl;
import com.yuki.Utils.FileUtils;
import com.yuki.Utils.ImageBytesUtils;
import com.yuki.Utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${user.filepath}")
    private String filePath;

    @Value("${user.UserAvatars}")
    private String userAvatarsPath;

    @Value("${user.CloudStorage}")
    private String CloudStorage;

    @Value("${user.RecycleBin}")
    private String RecycleBin;

    @Value("${user.PackageTemp}")
    private String PackageTemp;

    @RequiresGuest
    @PostMapping("/login")
    public ResponseResult userLogin(@Validated @RequestBody UserDto userDto){

        User user = userService.getUserByName(userDto.getUsername());

        if(ObjectUtils.isEmpty(user)){
            return ResponseResult.fail("用户不存在");
        }
        if (!user.getPassword().equals(SecureUtil.md5(userDto.getPassword()))) {
            return ResponseResult.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
//        response.setHeader("token", jwt);
//        response.setHeader("Access-control-Expose-Headers","token");
        String avatar_url = user.getAvatar();
        try {
            String base64Image = ImageBytesUtils.getImageBase64ByLocalUrl(avatar_url);
            user.setAvatar(base64Image);
        }catch (Exception e){
            System.out.println("用户没有头像");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);

        return ResponseResult.success(200,
                "登录成功! "+user.getUsername()+" 欢迎回来~",
                MapUtil.builder()
                        .put("userInfo",userVo)
                        .put("token",jwt).map());

    }

    @RequiresGuest
    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody UserDto userDto){

        if(userDto.getUsername().length() < 6 ||
                userDto.getPassword().length() < 6){
            return ResponseResult.fail("注册失败，用户名和密码的长度必须都在6位数以上!");
        }

        if (!userDto.getPassword().equals(userDto.getConfirm())){
            return ResponseResult.fail("注册失败，两次密码不一致");
        }
        try {
            userService.userRegister((User)userDto);
        } catch (Exception e){
            return ResponseResult.fail("注册失败,"+e.getMessage());
        }

        User userByName = userService.getUserByName(userDto.getUsername());
        Long id = userByName.getId();
        String userId = id.toString();

        FileUtils.makeDir(filePath+userAvatarsPath+"/"+userId);
        FileUtils.makeDir(filePath+CloudStorage+"/"+userId);
        FileUtils.makeDir(filePath+RecycleBin+"/"+userId);
        FileUtils.makeDir(filePath+PackageTemp+"/"+userId);

        return ResponseResult.success(200,"注册成功!欢迎使用");
    }

    @RequiresAuthentication
    @PostMapping("/logout")
    public ResponseResult userLogout(){
        SecurityUtils.getSubject().logout();
        return ResponseResult.success(200,"登出成功!");
    }

}
