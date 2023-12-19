package com.yuki.Domain.Entity;

import com.yuki.Domain.Vo.FileVo;
import com.yuki.Domain.Vo.FolderVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesEntity {
    List<FileVo> fileList;
    List<FolderVo> folderList;
}
