package com.yuki.Domain.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {

    public String file_name;
    public String file_size;
    public String modified_date;
    public String file_path;

}
