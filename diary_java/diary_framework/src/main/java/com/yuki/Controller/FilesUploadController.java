package com.yuki.Controller;


import com.yuki.Domain.ResponseResult;
import com.yuki.Shiro.AccountProfile;
import com.yuki.Utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FilesUploadController {


    @Value("${user.filepath}")
    private String rootPath;

    @Value("${user.CloudStorage}")
    private String CloudStorage;

    @Value("${user.PackageTemp}")
    private String PackageTemp;

    @RequiresAuthentication
    @PostMapping("/files")
    public ResponseResult uploadFile(@RequestParam("files") MultipartFile files[],
                                     @RequestParam("targetPath") String targetPath){

        if (ObjectUtils.isEmpty(targetPath) || targetPath.equals("")){
            targetPath = "/";
        }

        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String cloudPath = rootPath+CloudStorage+"/"+id;


        for (MultipartFile multipartFile : files) {
            String filename = targetPath+"/"+multipartFile.getOriginalFilename();
            File file = new File(cloudPath+filename);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
                System.out.println("父级目录不存在,创建文件夹");
            }

            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseResult.success(200,"上传成功");
    }


}
