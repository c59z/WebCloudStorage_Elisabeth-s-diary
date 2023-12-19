package com.yuki.Domain.Dto;


import com.yuki.Domain.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends User {

    private String username;

    private String password;

    private String confirm;

    private String checkNum;

}
