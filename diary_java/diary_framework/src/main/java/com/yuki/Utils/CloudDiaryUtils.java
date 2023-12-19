package com.yuki.Utils;

import com.yuki.Domain.Const.CloudFileType;
import com.yuki.Domain.Entity.FilesEntity;
import com.yuki.Domain.Vo.FileVo;
import com.yuki.Domain.Vo.FolderVo;
import com.yuki.Shiro.AccountProfile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class CloudDiaryUtils {

    final static Long B_Limit = 1024L;
    final static Long KB_Limit = 1024L * B_Limit;
    final static Long MB_Limit = 1024L * KB_Limit;
    final static Long GB_Limit = 1024L * MB_Limit;

    /**
     * 获取指定目录下的所有文件(不使用)
     *
     * @param rootFolderPath
     * @return
     */
    public static HashMap<String, List<String>> getCloudDiaryAllFiles(String rootFolderPath) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        getAllFile(rootFolderPath, rootFolderPath, map);
        return map;
    }


    /**
     * 获取指定分类的所有文件
     *
     * @param rootFolderPath
     * @param type
     * @return
     */
    public static List<FileVo> GetSpecifiedTypeOfAllFiles(String rootFolderPath, String type) {
        List<FileVo> fileList = new ArrayList<>();
        List<String> fileType = new ArrayList<>();
        switch (type) {
            case "1":
                fileType = new CloudFileType().videoType;
                System.out.println(">>>>视频");
                break; // 视频
            case "2":
                fileType = new CloudFileType().imgType;
                System.out.println(">>>>图片");
                break; // 图片
            case "3":
                fileType = new CloudFileType().soundType;
                System.out.println(">>>>音乐");
                break; // 音乐
            case "4":
                fileType = new CloudFileType().docType;
                System.out.println(">>>>文档");
                break; // 文档
            default:
                break;
        }
        getSpecifiedTypeFiles(rootFolderPath, rootFolderPath, fileList, fileType);
        return fileList;
    }


    /**
     * 获取用户搜索的文件或文件夹
     * @param rootFolderPath
     * @param keyword
     * @return
     */
    public static FilesEntity GetSearchedFiles(String rootFolderPath, String keyword) {
        List<FileVo> fileList = new ArrayList<>();
        List<FolderVo> folderList = new ArrayList<>();
        System.out.println("关键词为: " + keyword);
        getSearchedFiles(rootFolderPath, rootFolderPath, fileList, folderList, keyword);
        FilesEntity entity = new FilesEntity(fileList,folderList);
        return entity;
    }

    /**
     * 遍历用户存储空间的每一个文件夹和文件，把符合条件的加入对应List中
     * @param rootFolderPath
     * @param currentFolderPath
     * @param fileVoList
     * @param folderList
     * @param keyword
     */
    private static void getSearchedFiles(String rootFolderPath, String currentFolderPath, List<FileVo> fileVoList, List<FolderVo> folderList, String keyword) {
        File folder = new File(currentFolderPath);
        // 如果文件夹存在
        if (folder.exists() && folder.isDirectory()) {
            // 获取当前文件夹的所有文件名称
            File[] files = folder.listFiles();
            // 如果文件夹不是空的
            if (files != null) {
                // 这个list用于存放当前目录的所有文件名称
                List<String> fileList = new ArrayList<>();
                // 遍历每一个文件
                for (File file : files) {
                    // 如果文件是一个文件夹则会递归
                    if (file.isDirectory()) {
                        // 获取这个文件的相对路径 ，原理：获取当前文件的绝对路径，然后减去传过来的上一级目录的路径长度
                        String relativePath = file.getAbsolutePath().substring(rootFolderPath.length());
                        // 如果这个文件夹名称中存在用户输入的关键字就把它加入到list中
                        if (file.getName().contains(keyword)) {
                            // 获取相对路径
                            FolderVo folderVo = getFolderVo(file);
                            folderVo.setFile_path(FileUtils.filePathReset(relativePath));
                            folderList.add(folderVo);
                        }
                        // 递归遍历子文件夹
                        getSearchedFiles(rootFolderPath, file.getAbsolutePath(), fileVoList, folderList, keyword);
                        // 将这个目录的相对路径存入list中
                        fileList.add(relativePath);
                    } else {
                        // 如果这个文件名称中存在用户输入的关键字就把它加入到list中
                        if (file.getName().contains(keyword)) {
                            // 获取相对路径
                            String relativePath = FileUtils.filePathReset(file.getAbsolutePath().substring(rootFolderPath.length()));
                            FileVo fileVo = getFileVo(file);
                            fileVo.setFile_path(relativePath);
                            fileVoList.add(fileVo);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在或不是一个文件夹");
        }
    }


    /**
     * 获取指定路径下的文件夹
     *
     * @param currentPath
     */
    public static FilesEntity getCurrentFolder(String rootPath,String currentPath) {
        File folder = new File(currentPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

//            List<String> fileList = new ArrayList<>();
//            List<String> folderList = new ArrayList<>();
            List<FileVo> fileList = new ArrayList<>();
            List<FolderVo> folderList = new ArrayList<>();


            for (File file : files) {
                if (file.isDirectory()) {
                    String relativePath = FileUtils.filePathReset(file.getAbsolutePath().substring(rootPath.length()));
                    FolderVo folderVo = getFolderVo(file);
                    folderVo.setFile_path(relativePath);
                    folderList.add(folderVo);
                } else if (file.isFile()) {
                    String relativePath = FileUtils.filePathReset(file.getAbsolutePath().substring(rootPath.length()));
                    FileVo fileVo = getFileVo(file);
                    fileVo.setFile_path(relativePath);
                    fileList.add(fileVo);
                }
            }

            // 排序
//            Collections.sort(fileList, (file1, file2) -> file1.compareTo(file2));

            FilesEntity filesEntity = new FilesEntity(fileList, folderList);
            return filesEntity;
        } else {
            return new FilesEntity();
        }
    }


    /**
     * 将传过来的文件转为FileVo
     *
     * @param file
     * @return
     */
    private static FileVo getFileVo(File file) {
        String file_name = "";
        String file_size = "";
        Long file_lastModifiedMillis = 0L;
        file_name = file.getName();
        file_lastModifiedMillis = file.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastModifiedDate = new Date(file_lastModifiedMillis);
        String formattedLastModifiedDate = sdf.format(lastModifiedDate);
        long length = file.length();
        if (length < B_Limit) {
            file_size = String.valueOf(length) + " 字节";
        } else if (length < KB_Limit) {
            file_size = String.format("%.2f", (float) length / B_Limit) + " KB";
        } else if (length < MB_Limit) {
            file_size = String.format("%.2f", (float) length / KB_Limit) + " MB";
        } else if (length < GB_Limit) {
            file_size = String.format("%.2f", (float) length / MB_Limit) + " GB";
        }
        FileVo fileVo = new FileVo(file_name, file_size, formattedLastModifiedDate, null);
        return fileVo;
    }

    /**
     * 将传过来的文件转为FolderVo
     *
     * @param file
     * @return
     */
    private static FolderVo getFolderVo(File file) {
        String folder_name = "";
        Long folder_lastModifiedMillis = 0L;
        folder_name = file.getName();
        folder_lastModifiedMillis = file.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastModifiedDate = new Date(folder_lastModifiedMillis);
        String formattedLastModifiedDate = sdf.format(lastModifiedDate);
        FolderVo folderVo = new FolderVo(folder_name, formattedLastModifiedDate, null);
        return folderVo;
    }


    /**
     * 递归获取指定目录下的所有文件夹与文件(不使用)
     *
     * @param rootFolderPath
     * @param currentFolderPath
     * @param map
     */
    private static void getAllFile(String rootFolderPath, String currentFolderPath, HashMap<String, List<String>> map) {
        File folder = new File(currentFolderPath);
        // 如果文件夹存在
        if (folder.exists() && folder.isDirectory()) {
            // 获取当前文件夹的所有文件名称
            File[] files = folder.listFiles();
            // 如果文件夹不是空的
            if (files != null) {
                // 这个list用于存放当前目录的所有文件名称
                List<String> fileList = new ArrayList<>();
                // 遍历每一个文件
                for (File file : files) {
                    // 如果文件是一个文件夹则会递归
                    if (file.isDirectory()) {
                        // 获取这个文件的相对路径 ，原理：获取当前文件的绝对路径，然后减去传过来的上一级目录的路径长度
                        String relativePath = file.getAbsolutePath().substring(rootFolderPath.length());
                        // 递归遍历子文件夹
                        getAllFile(rootFolderPath, file.getAbsolutePath(), map);
                        // 将这个目录的相对路径存入list中
                        fileList.add(relativePath);
                    } else {
                        fileList.add(file.getName());
                    }
                }
                String relativeFolder = currentFolderPath.substring(rootFolderPath.length());
                map.put(relativeFolder, fileList);
            }
        } else {
            System.out.println("文件夹不存在或不是一个文件夹");
        }
    }


    /**
     * 遍历所有文件夹把指定类型的文件存到fileVoList中
     *
     * @param rootFolderPath
     * @param currentFolderPath
     * @param fileVoList
     * @param fileType
     */
    private static void getSpecifiedTypeFiles(String rootFolderPath, String currentFolderPath, List<FileVo> fileVoList, List<String> fileType) {
        File folder = new File(currentFolderPath);
        // 如果文件夹存在
        if (folder.exists() && folder.isDirectory()) {
            // 获取当前文件夹的所有文件名称
            File[] files = folder.listFiles();
            // 如果文件夹不是空的
            if (files != null) {
                // 这个list用于存放当前目录的所有文件名称
                List<String> fileList = new ArrayList<>();
                // 遍历每一个文件
                for (File file : files) {
                    // 如果文件是一个文件夹则会递归
                    if (file.isDirectory()) {
                        // 获取这个文件的相对路径 ，原理：获取当前文件的绝对路径，然后减去传过来的上一级目录的路径长度
                        String relativePath = file.getAbsolutePath().substring(rootFolderPath.length());
                        // 递归遍历子文件夹
                        getSpecifiedTypeFiles(rootFolderPath, file.getAbsolutePath(), fileVoList, fileType);
                        // 将这个目录的相对路径存入list中
                        fileList.add(relativePath);
                    } else {
                        // 如果是一个文件,则判断它的文件类型
                        String fileExtension = "." + FilenameUtils.getExtension(file.getName());
                        if (fileType.contains(fileExtension)) {
                            // 获取相对路径
                            String relativePath = FileUtils.filePathReset(file.getAbsolutePath().substring(rootFolderPath.length()));
                            FileVo fileVo = getFileVo(file);
                            fileVo.setFile_path(relativePath);
                            fileVoList.add(fileVo);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在或不是一个文件夹");
        }
    }
}
