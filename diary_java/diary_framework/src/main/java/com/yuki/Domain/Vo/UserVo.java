package com.yuki.Domain.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuki.Domain.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;

    private String username;

    private String email;

    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss",timezone = "GMT+8")
    private Date createdate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss",timezone = "GMT+8")
    private Date updatedate;

}
