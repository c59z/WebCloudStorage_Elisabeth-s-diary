package com.yuki.Domain.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserChangeDto extends UserDto{
    private String username;

    private String passwordOld;

    private String passwordNew;

    private String confirm;

    private Date updatedate;
}
