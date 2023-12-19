package com.yuki.Service.impl;

import cn.hutool.crypto.SecureUtil;
import com.yuki.Domain.Dto.UserChangeDto;
import com.yuki.Domain.Entity.User;
import com.yuki.Mapper.UserMapper;
import com.yuki.Service.UserService;
import com.yuki.Shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * (User表)服务实现类
 *
 * @author makejava
 * @since 2023-09-11 17:29:15
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void userRegister(User user) {
        User user_temp = this.getUserByName(user.getUsername());
        if(!ObjectUtils.isEmpty(user_temp)){
            throw new RuntimeException("用户已存在，无法注册");
        }
        // TODO 将加密后的密码插入数据库
        user.setPassword(SecureUtil.md5(user.getPassword()));
        // 添加创建日期
        Date currentDate = new Date();
        user.setCreatedate(currentDate);
        //5.将对象保存到数据库中
        userMapper.userRegister(user);
    }

    @Override
    public User getUserByName(String name) {
        User user = userMapper.getUserByName(name);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<String> getUserPermsById(Long id) {
        String roleName = userMapper.getUserRoleById(id);
        List<String> perms = userMapper.getUserPermByRoleName(roleName);
        return perms;
    }

    @Override
    public boolean changePassword(UserChangeDto userDto) {
        // 通过用户名从数据库获取的用户
        User user_temp = this.getUserByName(userDto.getUsername());
        Subject subject = SecurityUtils.getSubject();
        AccountProfile userProfile =  (AccountProfile) subject.getPrincipal();
        if (!Objects.equals(userProfile.getId(), user_temp.getId())){
            throw new RuntimeException("修改密码失败,用户错误");
        }
        if(ObjectUtils.isEmpty(user_temp)){
            throw new RuntimeException("修改密码失败,用户不存在");
        }
        if(!user_temp.getPassword().equals(SecureUtil.md5(userDto.getPasswordOld()))){
            System.out.println(user_temp.getPassword());
            System.out.println(SecureUtil.md5(userDto.getPassword()));
            throw new RuntimeException("修改密码失败,原密码错误");
        }
        if (userDto.getPasswordNew().length()<6){
            throw new RuntimeException("修改密码失败,密码长度不能小于6位");
        }
        if(!userDto.getPasswordNew().equals(userDto.getConfirm())){
            throw new RuntimeException("修改密码失败,两次密码不一致");
        }


        // TODO 将加密后的密码插入数据库
        userDto.setPasswordNew(SecureUtil.md5(userDto.getPasswordNew()));
        userDto.setId(user_temp.getId());
        // 添加创建日期
        Date currentDate = new Date();
        userDto.setUpdatedate(currentDate);
        //5.将对象保存到数据库中
        boolean b = userMapper.changeUserPassword(userDto);
        return b;
    }

    @Override
    public boolean changeUserAvatar(String imageUrl, Long id) {
        boolean b = userMapper.changeUserAvatar(imageUrl, id);
        return b;
    }

    @Override
    public int getUserStorageSize(Long id) {
        int userStorageSize = userMapper.getUserStorageSizeById(id);
        return userStorageSize;
    }
}



 

