package com.yuki.Utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    /**
     * 新建文件夹
     *
     * @param path
     * @return
     */
    public static boolean makeDir(String path) {

        File folder = new File(path);

        // 检查文件夹是否已经存在
        if (!folder.exists()) {
            // 使用mkdirs()方法创建文件夹，包括父文件夹（如果不存在的话）
            if (folder.mkdirs()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 重命名
     *
     * @param pathOld
     * @param pathNew
     * @return
     */
    public static boolean renameFile(String pathOld, String pathNew) {
        File fileOld = new File(pathOld);
        File fileNew = new File(pathNew);
        System.out.println("旧地址:" + pathOld);
        System.out.println("新地址:" + pathNew);
        if (fileOld.renameTo(fileNew)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取指定文件夹的所有大小(包括子文件夹)
     *
     * @param path
     * @return
     */
    public static Long getUsedStorageSize(String path) {
        File dir = new File(path);
        Long size = 0L;
        if (dir.exists() && dir.isDirectory()) {
            size = org.apache.commons.io.FileUtils.sizeOfDirectory(dir);
        } else {
            throw new RuntimeException("文件夹不存在");
        }
        return size;
    }

    /**
     * 移动文件或文件夹
     *
     * @param sourcePath
     * @param targetPath
     */
    public static void moveFile(List<String> sourcePath, List<String> targetPath) {
        List<File> sourceFiles = new ArrayList<>();
        List<File> targetFiles = new ArrayList<>();
        for (String s : sourcePath) {
            System.out.println("源路径: " + s);
            File sourceFile = new File(s);
            sourceFiles.add(sourceFile);
        }
        for (String s : targetPath) {
            System.out.println("目标路径: " + s);
            File targetFile = new File(s);
            targetFiles.add(targetFile);
        }

        for (int i = 0; i < targetFiles.size(); i++) {
            sourceFiles.get(i).renameTo(targetFiles.get(i));
        }
    }


    /**
     * 删除指定路径下的所有文件与文件夹
     *
     * @param targetPath
     * @return
     */
    public static void deleteAll(String targetPath) {
        File rootFolder = new File(targetPath);
        if (rootFolder.exists() && rootFolder.isDirectory()) {
            File[] files = rootFolder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    // 递归删除文件夹
                    deleteFolder(file);
                }
            }
        }
    }

    /**
     * 递归删除文件夹
     *
     * @param folder
     */
    private static void deleteFolder(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }

    /**
     * 删除回收站中用户选择的内容
     *
     * @param Path
     * @param deleteList
     */
    public static void deleteFiles(String Path, List<String> deleteList) {
        File rootPath = new File(Path);
        if (rootPath.exists() && rootPath.isDirectory()) {
            File[] files = rootPath.listFiles();
            for (int i = 0; i < files.length; i++) {
                for (int j = 0; j < deleteList.size(); j++) {
                    if (files[i].getName().equals(deleteList.get(j))) {
                        if (files[i].isDirectory()) {
                            deleteFolder(files[i]);
                        } else {
                            files[i].delete();
                        }
                    }
                }
            }
        }
    }

    /**
     * 把一个目录下的所有文件打包成zip
     * @param path     要压缩的文件路径
     * @param tempPath 要把压缩文件放到哪里
     */
    public static String generateFile(String path, String tempPath) throws Exception {
        File file = new File(path);
        // 压缩文件的路径不存在
        if (!file.exists()) {
            throw new Exception("路径 " + path + " 不存在文件");
        }
        String fileName = "";
        int i = path.lastIndexOf("/");
        if (i != -1) {
            fileName = path.substring(i + 1) + ".zip";
        } else {
            fileName = "file.zip";
        }
        // 用于存放压缩文件的文件夹
        File compress = new File(tempPath);
        // 如果文件夹不存在，进行创建
        if (!compress.exists()) {
            compress.mkdirs();
        }
        // 目的压缩文件
        String generateFileName = tempPath + "/" + fileName;
        // 输入流 表示从一个源读取数据
        // 输出流 表示向一个目标写入数据
        // 输出流
        FileOutputStream outputStream = new FileOutputStream(generateFileName);
        // 压缩输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
        generateFile(zipOutputStream, file, "");
        System.out.println("源文件位置：" + file.getAbsolutePath() + "，目的压缩文件生成位置：" + generateFileName);
        // 关闭 输出流
        zipOutputStream.close();
        return generateFileName;
    }

    /**
     * 把用户选中的文件或文件夹打包成zip
     * @param downloadFilePath
     * @param tempPath
     * @return
     * @throws Exception
     */
    public static String generateFileSelected(List<String> downloadFilePath, String tempPath) throws Exception {
        String fileName = "file.zip";
//        int i = path.lastIndexOf("/");
//        if (i != -1){
//            fileName = path.substring(i+1)+".zip";
//        }else{
//            fileName = "file.zip";
//        }
        // 用于存放压缩文件的文件夹
        File compress = new File(tempPath);
        // 如果文件夹不存在，进行创建
        if (!compress.exists()) {
            compress.mkdirs();
        }
        // 目的压缩文件
        String generateFileName = tempPath + "/" + fileName;
        List<File> files = new ArrayList<>();
        for (String s : downloadFilePath) {
            files.add(new File(s));
        }

        File zipFile = new File(generateFileName);
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ZipOutputStream zos = new ZipOutputStream(bos)) {

            for (File file : files) {
                generateFiles(zos, file, "");
            }
        }
        return generateFileName;
    }

    /**
     *
     * @param zos
     * @param file
     * @param dir
     * @throws IOException
     */
    private static void generateFiles(ZipOutputStream zos, File file, String dir) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                generateFiles(zos, f, dir + file.getName() + "/");
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            ZipEntry zipEntry = new ZipEntry(dir + file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            fis.close();
            bis.close();
        }
    }


    /**
     * @param out  输出流
     * @param file 目标文件
     * @param dir  文件夹
     * @throws Exception
     */
    private static void generateFile(ZipOutputStream out, File file, String dir) throws Exception {
        // 当前的是文件夹，则进行一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                generateFile(out, files[i], dir + files[i].getName());
            }
        } else { // 当前是文件
            // 输入流
            FileInputStream inputStream = new FileInputStream(file);
            // 标记要打包的条目
            out.putNextEntry(new ZipEntry(dir));
            // 进行写操作
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
            // 关闭输入流
            inputStream.close();
        }
    }

    /**
     * 重新格式化url
     * @param filePath
     * @return
     */
    public static String filePathReset(String filePath){
        if (filePath == null) {
            return "/";
        }else if(!filePath.contains("\\")){
            return filePath;
        }

        // 将所有的\替换为/
        String realPath = filePath.replace("\\", "/");

        // 检查字符串长度，如果大于1，则在开头添加/
        if (realPath.length() > 1) {
            realPath = "/" + realPath;
        }

        // 检测相邻的\\，只保留第一个
        realPath = realPath.replaceAll("/+", "/");

        return realPath;
    }


    public static void main(String[] args) {
        /*
        String path = "D:/code/Project/java/前后端分离-伊丽莎白的日记/CloudDiary/CloudStorage/1/图片";
        String tempPath = "D:/code/Project/java/前后端分离-伊丽莎白的日记/CloudDiary/PackageTemp/1";
        try {
            generateFile(path,tempPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
         */
    }


}
