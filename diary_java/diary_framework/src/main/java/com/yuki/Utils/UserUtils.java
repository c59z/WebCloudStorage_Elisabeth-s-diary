package com.yuki.Utils;

import com.yuki.Shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class UserUtils {
    public static AccountProfile getLoginUser(){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile loginUser = (AccountProfile)subject.getPrincipal();
        return loginUser;
    }
}
