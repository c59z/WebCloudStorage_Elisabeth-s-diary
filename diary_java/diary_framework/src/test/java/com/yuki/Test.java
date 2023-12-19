package com.yuki;

import com.yuki.Domain.Vo.UserVo;
import com.yuki.Utils.ImageBytesUtils;
import org.springframework.beans.BeanUtils;

import javax.websocket.server.PathParam;
import java.io.File;
import java.util.*;

public class Test {
    public static void main01(String[] args) {

        HashMap<String, List<String>> map = new HashMap<String, List<String>>();


        System.out.println("---------------------");
        // 假定这里用户的根目录
        String rootFolderPath = "D:/code/Project/java/WebCloudStorage_Elisabeth's diary/CloudDiary/CloudStorage/1";
        getAllFile(rootFolderPath,rootFolderPath,map);

        for(Map.Entry<String,List<String>> entry:map.entrySet()){
            String folderName = entry.getKey();
            List<String> fileList = entry.getValue();

            System.out.println("文件夹: "+folderName);
            System.out.println("文件: "+fileList);
        }
    }

    public static void main02(String[] args) {
        String avatar_url = "D:/code/Project/java/WebCloudStorage_Elisabeth's diary/CloudDiary/UserAvatars/1/20230915203754_1_.jpg";
        String base64Image = ImageBytesUtils.getImageBase64ByLocalUrl(avatar_url);
        System.out.println(base64Image);
    }

    public static void getAllFile(String rootFolderPath, String currentFolderPath,HashMap<String, List<String>> map){
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
                        getAllFile(rootFolderPath,file.getAbsolutePath(),map);
                        // 将这个目录的相对路径存入list中
                        fileList.add(relativePath);
                    }else{
                        fileList.add(file.getName());
                    }
                }
                String relativeFolder  = currentFolderPath.substring(rootFolderPath.length());
                map.put(relativeFolder,fileList);
            }
        } else {
            System.out.println("文件夹不存在或不是一个文件夹");
        }
    }

}
