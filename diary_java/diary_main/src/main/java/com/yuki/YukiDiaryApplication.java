package com.yuki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yuki.mapper")
public class YukiDiaryApplication {

    /*
    Author:
     */
    public static void main(String[] args) {
        SpringApplication.run(YukiDiaryApplication.class,args);
    }
}