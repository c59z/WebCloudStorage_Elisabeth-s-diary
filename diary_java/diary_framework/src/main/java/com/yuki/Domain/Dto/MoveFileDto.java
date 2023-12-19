package com.yuki.Domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveFileDto {
    List<String> fileList;
    String sourcePath;
    String targetPath;
}
