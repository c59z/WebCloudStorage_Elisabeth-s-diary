package com.yuki.Utils;


import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageBytesUtils {

    public static String getUrlBase64Image(byte[] byteImage){
        String base64 = Base64.getEncoder().encodeToString(byteImage);
        return base64;
    }

    public static String getImageBase64ByLocalUrl(String urlImage){
        try {
            Path imagePath = Paths.get(urlImage);
            // 读取图片文件为字节数组
            byte[] imageBytes = FileCopyUtils.copyToByteArray(new File(imagePath.toString()));
            // 使用Base64编码转换为Base64字符串
            String base64Image = getUrlBase64Image(imageBytes);
            // 返回Base64字符串
            return base64Image;
        }catch (Exception e ){
            throw new RuntimeException(e.getMessage());
        }

    }

    public static void saveByteByBase64(String base64Image, String imagePath){
        byte[] bytes = Base64.getDecoder().decode(base64Image);
        try (OutputStream outputStream = new FileOutputStream(imagePath)) {
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return;
    }

}
