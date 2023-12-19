package com.yuki.Controller;

import cn.hutool.core.map.MapUtil;
import com.yuki.Domain.Dto.AvatarBase64;
import com.yuki.Domain.Dto.UserChangeDto;
import com.yuki.Domain.Entity.User;
import com.yuki.Domain.ResponseResult;
import com.yuki.Domain.Vo.UserVo;
import com.yuki.Service.impl.UserServiceImpl;
import com.yuki.Shiro.AccountProfile;
import com.yuki.Utils.ImageBytesUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/userCenter")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Value("${user.filepath}")
    private String filePath;

    @Value("${user.UserAvatars}")
    private String userAvatarsPath;


    @RequiresAuthentication
    @PutMapping("/changePwd")
    public ResponseResult changePwd(@RequestBody UserChangeDto user){
        boolean b = userService.changePassword(user);
        if (b){
            SecurityUtils.getSubject().logout();
            return ResponseResult.success(200,"修改密码成功!请重新登录");
        }else{
            return ResponseResult.fail(400,"修改密码失败");
        }
    }

    @RequiresAuthentication
    @PostMapping("/uploadAvatar")
    public ResponseResult uploadAvatar(@RequestParam("file") MultipartFile file){
        System.out.println("文件名: "+file.getOriginalFilename());
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("图片错误!");
        }
        String base64Image = ImageBytesUtils.getUrlBase64Image(bytes);

        return ResponseResult.success(200,"上传成功!", MapUtil.builder()
                .put("previewPicture",base64Image).map());
    }

    @RequiresAuthentication
    @PutMapping("/saveAvatar")
    public ResponseResult saveAvatar(@RequestBody AvatarBase64 avatar){
        if (ObjectUtils.isEmpty(avatar.getAvatar())){
            throw new RuntimeException("请选择要上传的头像~");
        }
        Subject subject = SecurityUtils.getSubject();
        AccountProfile user = (AccountProfile)subject.getPrincipal();

        // 删除用户之前的头像
        String avatarFolderPath = filePath+userAvatarsPath+"/"+user.getId().toString();
        File folder = new File(avatarFolderPath);
        if (folder.isDirectory() && folder.exists()){
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.delete()){
                    System.out.println("删除成功: "+file.getName());
                }else{
                    System.out.println("删除失败: "+file.getName());
                }
            }
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
        String avatarName = sdf.format(d) + "_" + user.getId().toString() + "_" + ".jpg";
        String imagePath = filePath+userAvatarsPath+"/"+user.getId().toString()+"/"+avatarName;
        ImageBytesUtils.saveByteByBase64(avatar.getAvatar(),imagePath);
        boolean b = userService.changeUserAvatar(imagePath, user.getId());
        if (b){
            return ResponseResult.success(200,"保存成功!");
        }else{
            return ResponseResult.success(400,"保存头像失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("getUserInfo")
    public ResponseResult getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile userProfile = (AccountProfile)subject.getPrincipal();
        User user = userService.getUserById(userProfile.getId());
        String avatar_url = user.getAvatar();
        String base64Image = ImageBytesUtils.getImageBase64ByLocalUrl(avatar_url);
        user.setAvatar(base64Image);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return ResponseResult.success(200,
                "重新获取用户信息: "+user.getUsername(),
                MapUtil.builder()
                        .put("userInfo",userVo).map());

    }


}
