package com.yuki.Domain.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    
    private Long id;
    
    private String username;
    
    private String password;
    
    private String email;
    
    private String avatar;

    @TableField("role_id")
    private Integer role_id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updatedate;

    private int status;

}

