package com.yuki.Service;

import com.yuki.Domain.Dto.UserChangeDto;
import com.yuki.Domain.Entity.User;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-09-11 17:29:15
 */
public interface UserService {

    public void userRegister(User user);

    public User getUserByName(String name);

    public User getUserById(Long id);

    public List<String> getUserPermsById(Long id);

    public boolean changePassword(UserChangeDto userDto);

    public boolean changeUserAvatar(String imageUrl,Long id);

    public int getUserStorageSize(Long id);

}


