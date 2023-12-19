package com.yuki.Controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequiresAuthentication //这个注解规定这个url需要登录用户才能访问
    @GetMapping("/hello")
    public String TestHello(){
        return "这里是/hello页面";
    }

    @RequiresPermissions(value={"sys:root"})
    @GetMapping("/admin")
    public String TestLog(){
        return "这里是/admin页面，一般人进不来";
    }


}
