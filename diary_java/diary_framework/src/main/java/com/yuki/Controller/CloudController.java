package com.yuki.Controller;


import cn.hutool.core.map.MapUtil;
import com.google.errorprone.annotations.Var;
import com.yuki.Domain.Dto.CreateNewFolderDto;
import com.yuki.Domain.Dto.DeleteDto;
import com.yuki.Domain.Dto.MoveFileDto;
import com.yuki.Domain.Dto.RenameDto;
import com.yuki.Domain.Entity.FilesEntity;
import com.yuki.Domain.ResponseResult;
import com.yuki.Domain.Vo.FileVo;
import com.yuki.Service.UserService;
import com.yuki.Shiro.AccountProfile;
import com.yuki.Utils.CloudDiaryUtils;
import com.yuki.Utils.FileUtils;
import com.yuki.Utils.UserUtils;
import jdk.javadoc.internal.doclets.toolkit.taglets.UserTaglet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/Cloud")
@RestController
public class CloudController {

    final static Long B_Limit = 1024L;
    final static Long KB_Limit = 1024L * B_Limit;
    final static Long MB_Limit = 1024L * KB_Limit;
    final static Long GB_Limit = 1024L * MB_Limit;

    @Value("${user.filepath}")
    private String filePath;

    @Value("${user.CloudStorage}")
    private String CloudStorage;

    @Value("${user.RecycleBin}")
    private String RecycleBin;

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户的所有文件路径(实际不使用)
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/getAllFiles")
    public ResponseResult getAllFiles(){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile userProfile = (AccountProfile) subject.getPrincipal();
        Long id = userProfile.getId();
        String rootFolderPath = filePath+CloudStorage+"/"+id.toString();
        System.out.println(rootFolderPath);
        HashMap<String, List<String>> allFiles = CloudDiaryUtils.getCloudDiaryAllFiles(rootFolderPath);
        return ResponseResult.success(200,"获取成功!", MapUtil.builder()
                .put("allFiles",allFiles).map());
    }

    /**
     * 返回用户存放的所有指定类型的文件
     * @param type 1: 视频 2:图片 3:音乐 4:文档
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/FileSort/{type}")
    public ResponseResult getSpecifiedTypeFiles(@PathVariable String type){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String rootPath = filePath+CloudStorage+"/"+id+"/";
        List<FileVo> fileVos = CloudDiaryUtils.GetSpecifiedTypeOfAllFiles(rootPath, type);
        FilesEntity entity = new FilesEntity();
        entity.setFileList(fileVos);
        return ResponseResult.success(200,"获取成功!",entity);
    }

    /**
     * 搜索指定的文件和文件夹
     * @param keyword
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/search")
    public ResponseResult getSearchedFile(@RequestParam("keyword") String keyword){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String rootPath = filePath+CloudStorage+"/"+id+"/";
        FilesEntity entity = CloudDiaryUtils.GetSearchedFiles(rootPath,keyword);
        return ResponseResult.success(200,"获取成功!",entity);
    }

    /**
     * 获取当前用户的总容量/使用容量与总容量的百分比
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/getUsedStorage")
    public ResponseResult getUsedStorage(){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String rootPath = filePath+CloudStorage+"/"+id+"/";
        // 获取用户文件的总大小
        Long size = FileUtils.getUsedStorageSize(rootPath);
        // 获取用户数据表中最大可用空间的大小
        int userStorageSize = userService.getUserStorageSize(Long.valueOf(id));
        Long allSize = userStorageSize * MB_Limit;

        double percentage = (double)size / (double)allSize * 100;
        String percentageStr = String.format("%.2f%%",percentage);
//        System.out.println("已经使用了 "+percentage+"%空间");
        String file_size = "";
        if (size < B_Limit){
            file_size = String.valueOf(size)+" 字节";
        }else if (size < KB_Limit){
            file_size = String.format("%.2f", (float) size / B_Limit) + " KB";
        }else if (size < MB_Limit){
            file_size = String.format("%.2f", (float) size / KB_Limit) + " MB";
        }else if (size < GB_Limit){
            file_size = String.format("%.2f", (float) size / MB_Limit) + " GB";
        }

        return ResponseResult.success(200,"获取用户当前存储空间",MapUtil.builder()
                .put("usedStorage",file_size)
                .put("size",String.valueOf(userStorageSize)+" GB")
                .put("percentage",percentageStr).map());
    }


    /**
     * 获取用户当前路径下的文件夹(不进行递归,节省性能)
     * @param path
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/getFilesByPath")
    public ResponseResult getFileByPath(@PathParam(value = "path") String path){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile userProfile = (AccountProfile) subject.getPrincipal();
        Long id = userProfile.getId();
        String rootFolderPath = filePath+CloudStorage+"/"+id.toString();
        String currentPath = rootFolderPath+path;
//        System.out.println(currentPath);
        FilesEntity currentFolder = CloudDiaryUtils.getCurrentFolder(rootFolderPath,currentPath);
        return ResponseResult.success(200,"获取当前URL下的文件",currentFolder);
    }


    /**
     * 重命名文件(单个)
     * @param renameDto
     * @return
     */
    @RequiresAuthentication
    @PutMapping("/rename")
    public ResponseResult renameFile(@RequestBody RenameDto renameDto){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile loginUser = (AccountProfile)subject.getPrincipal();
        String id = loginUser.getId().toString();
        String userPath = filePath+CloudStorage+"/"+id+"/";
        String folderPath = renameDto.getFolderPath();
        String fileNameNew = userPath+folderPath+"/"+renameDto.getFileNameNew();
        String fileNameOld = userPath+folderPath+"/"+renameDto.getFileNameOld();
        boolean b = FileUtils.renameFile(fileNameOld, fileNameNew);
        if (!b){
            // 重命名失败
            return ResponseResult.fail("重命名失败");
        }
        ResponseResult responseResult = getFileByPath(renameDto.getFolderPath());
        return responseResult;
    }

    /**
     * 将文件移入回收站
     * @param deleteDto
     * @return
     */
    @RequiresAuthentication
    @PutMapping("/toRecycleBin")
    public ResponseResult moveFileToRecycleBin(@RequestBody DeleteDto deleteDto){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String folderPath = deleteDto.getFolderPath();
        String sourceRootPath = filePath+CloudStorage+"/"+id+folderPath+"/";
        String targetRootPath = filePath+RecycleBin+"/"+id+"/";
        List<String> deleteList = deleteDto.getDeleteList();
        List<String> sourceFiles = new ArrayList<>();
        List<String> targetFiles = new ArrayList<>();
        for (String s : deleteList) {
            sourceFiles.add(sourceRootPath+s);
            targetFiles.add(targetRootPath+s);
        }
        FileUtils.moveFile(sourceFiles,targetFiles);

        return ResponseResult.success(200,"移动到回收站成功!");
    }

    /**
     * 获取回收站中的所有文件
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/getRecycleBinFiles")
    public ResponseResult getRecycleBinFiles(){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String rootFolderPath = filePath+RecycleBin+"/"+id+"/";
        System.out.println(rootFolderPath);
        FilesEntity currentFolder = CloudDiaryUtils.getCurrentFolder(rootFolderPath,rootFolderPath);
        return ResponseResult.success(200,"获取回收站下的文件",currentFolder);
    }


    /**
     * 清空回收站里的所有文件
     * @return
     */
    @RequiresAuthentication
    @DeleteMapping("/RecycleBin/deleteAll")
    public ResponseResult deleteAllFile(){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String targetPath = filePath+RecycleBin+"/"+id+"/";
        FileUtils.deleteAll(targetPath);
        return ResponseResult.success(200,"已清除回收站的所有内容");
    }

    /**
     * 清除回收站中的指定内容
     * @param deleteDto
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/RecycleBin/deleteFiles")
    public ResponseResult deleteFiles(@RequestBody DeleteDto deleteDto){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String targetPath = filePath+RecycleBin+"/"+id+"/";
        if (ObjectUtils.isEmpty(deleteDto.getDeleteList())){
            return ResponseResult.fail("您尚未选择要删除的文件");
        }
        List<String> deleteList = deleteDto.getDeleteList();
        FileUtils.deleteFiles(targetPath,deleteList);
        return ResponseResult.success(200,"清除指定内容成功");
    }

    /**
     * 把文件从回收站中移回文件夹中
     * @param fileDto
     * @return
     */
    @RequiresAuthentication
    @PutMapping("/RecycleBin/reduce")
    public ResponseResult reduceFiles(@RequestBody MoveFileDto fileDto){
        // TODO 把文件从回收站中移回文件夹中
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        // 源路径根目录
        String sourcePath = filePath+RecycleBin+"/"+id+"/";
        // 目标路径根目录
        String targetPath = filePath+CloudStorage+"/"+id+"/"+ fileDto.getTargetPath();
        List<String> sourceFileList = new ArrayList<>();
        List<String> targetFileList = new ArrayList<>();
        List<String> fileList = fileDto.getFileList();
        if (fileList.size() < 1){
            return ResponseResult.fail(400,"请选择要还原的文件");
        }

        for (String s : fileList) {
            sourceFileList.add(sourcePath+s);
            targetFileList.add(targetPath+s);
        }
        FileUtils.moveFile(sourceFileList,targetFileList);
        return ResponseResult.success(200,"文件还原成功!");
    }

    /**
     * 在全部文件中移动文件夹(不包括回收站)
     * @param fileDto
     * @return
     */
    @RequiresAuthentication
    @PutMapping("/moveFiles")
    public ResponseResult moveFiles(@RequestBody MoveFileDto fileDto){
        // TODO 把文件从回收站中移回文件夹中
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        // 源路径根目录
        String sourcePath = filePath+CloudStorage+"/"+id + fileDto.getSourcePath();
        // 目标路径根目录
        String targetPath = filePath+CloudStorage+"/"+id+ fileDto.getTargetPath();
        List<String> sourceFileList = new ArrayList<>();
        List<String> targetFileList = new ArrayList<>();
        List<String> fileList = fileDto.getFileList();
        if (fileList.size() < 1){
            return ResponseResult.fail(400,"请选择要移动的文件");
        }

        for (String s : fileList) {
            sourceFileList.add(sourcePath+s);
            targetFileList.add(targetPath+s);
        }
        FileUtils.moveFile(sourceFileList,targetFileList);
        return ResponseResult.success(200,"文件移动成功!");
    }


    @RequiresAuthentication
    @PutMapping("/createNewFolder")
    public ResponseResult createNewFolder(@RequestBody CreateNewFolderDto folderDto){
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String folderPath = filePath+CloudStorage+"/"+id +"/"+ folderDto.getFolderPath()+"/"+folderDto.getFolderName();
        FileUtils.makeDir(folderPath);

        return ResponseResult.success(200,"新建文件夹成功!");

    }

}
