package com.yuki.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    // 注入application.yml中配置好的图片存放地址
    @Value("${user.filepath}")
    private String filePath;


    // 自定义资源映射
    // 访问图片示例：http://localhost:3000/api/images/图片名称.jpg
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println(filePath);
        registry.addResourceHandler("/api/cloud/**")
                .addResourceLocations("file:"+ filePath);
    }

}
