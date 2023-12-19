package com.yuki.Mapper;


import com.yuki.Domain.Dto.UserChangeDto;
import com.yuki.Domain.Dto.UserDto;
import com.yuki.Domain.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-11 17:29:14
 */
@Mapper
public interface UserMapper {

    /**
     * 用户注册
     * @param user
     */
    public void userRegister(@Param("user")User user);

    /**
     * 通过用户名获取这个用户对象
     * @param name
     * @return
     */
    public User getUserByName(String name);

    /**
     * 通过用户id获取这个用户对象
     * @param id
     * @return
     */
    public User getUserById(Long id);

    /**
     * 通过用户id获取用户对应的角色名称
     * @param id
     * @return
     */
    public String getUserRoleById(Long id);

    /**
     * 通过角色名称获取这个角色的权限列表
     * @param roleName
     * @return
     */
    public List<String> getUserPermByRoleName(String roleName);

    /**
     * 修改用户的密码
     * @param userDto
     * @return
     */
    public boolean changeUserPassword(@Param("userDto")UserChangeDto userDto);

    public boolean changeUserAvatar(@Param("imageUrl")String imageUrl,@Param("userId") Long id);

    public int getUserStorageSizeById(Long id);

}

