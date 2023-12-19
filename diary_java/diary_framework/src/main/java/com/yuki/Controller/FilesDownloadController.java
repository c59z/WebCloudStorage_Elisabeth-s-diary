package com.yuki.Controller;

import com.yuki.Domain.Dto.DownloadDto;
import com.yuki.Domain.ResponseResult;
import com.yuki.Shiro.AccountProfile;
import com.yuki.Utils.FileUtils;
import com.yuki.Utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;


@RequestMapping("/dowmload")
@RestController
public class FilesDownloadController {


    @Value("${user.filepath}")
    private String rootPath;

    @Value("${user.CloudStorage}")
    private String CloudStorage;

    @Value("${user.PackageTemp}")
    private String PackageTemp;

    /**
     * 下载单个文件(直接返回,不分段)
     * @param download
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/one-file")
    public ResponseEntity<Resource> downloadFile(@RequestBody DownloadDto download){
        Subject subject = SecurityUtils.getSubject();
        AccountProfile loginUser = (AccountProfile)subject.getPrincipal();
        String id = String.valueOf(loginUser.getId());
        String filename = download.getSelectedList().get(0);
        String filePath = "file:" + rootPath+CloudStorage+"/"+id+download.getCurrentPath()+filename;
        System.out.println("path-------");
        System.out.println(filePath);
        try {
            Resource resource = new UrlResource(filePath);
            if (resource.exists() && resource.isReadable()){
                // 创建文件资源
                long fileLength = resource.contentLength();

                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment",filename);
                headers.set(HttpHeaders.CONTENT_ENCODING,"UTF-8");
                headers.setContentLength(fileLength);
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 当单个文件分段下载
     * @param request
     * @param response
     * @param selectedList
     * @param currentPath
     * @throws IOException
     * @throws IOException
     */
    @RequiresAuthentication
    @GetMapping("/one-file")
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathParam(value = "selectedList") String selectedList,
                         @PathParam(value = "currentPath") String currentPath) throws IOException, IOException {

        String decodeStr = URLDecoder.decode(selectedList, "UTF-8");
        String[] selectList = decodeStr.split(",");
        currentPath = URLDecoder.decode(currentPath,"UTF-8");

        Subject subject = SecurityUtils.getSubject();
        AccountProfile loginUser = (AccountProfile)subject.getPrincipal();
        String id = loginUser.getId().toString();
        String filename = selectList[0];
        String filePath = rootPath+CloudStorage+"/"+id+"/"+filename;
//        String filePath = rootPath+CloudStorage+"/"+id+"currentPath"+"/"+filename;

        File file = new File(filePath);
        long fileSize = file.length();

        System.out.println("path-------");
        System.out.println(filePath);
        System.out.println("end - path-------");

        response.setContentType("application/octect-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setHeader("Accept-Ranges", "bytes");

        String rangeHeader = request.getHeader("Range");
        if (rangeHeader == null){
            // (第一次下载)下载整个文件
            response.setHeader("Content-Length", String.valueOf(fileSize));
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.close();
        }else{
            // 继续下载文件
            long start = 0L;
            long end = fileSize -1L;
            String[] range = rangeHeader.split("=")[1].split("-");
            if (range.length == 1) {
                start = Long.parseLong(range[0]);
                end = fileSize - 1;
            } else {
                start = Long.parseLong(range[0]);
                end = Long.parseLong(range[1]);
            }
            long contentLength = end - start + 1;
            // 返回头里存放每次读取的开始和结束字节
            response.setHeader("Content-Length", String.valueOf(contentLength));
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            // 跳到第start字节
            in.skip(start);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            long bytesWritten = 0;
            while ((bytesRead = in.read(buffer)) != -1) {
                if (bytesWritten + bytesRead > contentLength) {
                    out.write(buffer, 0, (int) (contentLength - bytesWritten));
                    break;
                } else {
                    out.write(buffer, 0, bytesRead);
                    bytesWritten += bytesRead;
                }
            }
            in.close();
            out.close();
        }
    }


    /**
     * 将整个目录下的文件全部打包下载
     * @param response
     * @param targetPath
     */
    @RequiresAuthentication
    @GetMapping("/package-file")
    public void packageDownload(
            HttpServletResponse response,
            @PathParam(value = "targetPath") String targetPath){
        // 前半部分把传来的目录路径打包成zip
        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        String targetFullPath = rootPath+CloudStorage+"/"+id+"/"+targetPath;
        String tempFullPath = rootPath+PackageTemp+"/"+id+"/";
        String zipFilePath = "";
        try {
            zipFilePath = FileUtils.generateFile(targetFullPath,tempFullPath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        // 把zip文件返回给前端下载
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists() || !zipFile.isFile()){
            throw new RuntimeException("创建zip文件失败");
        }
        try {
            FileInputStream inputStream = new FileInputStream(zipFile);
            response.reset();
            response.setCharacterEncoding("utf-8");
            // 设置相关格式
            response.setContentType("application/force-download");
            String filename = zipFile.getName();
            // 解决下载文件名乱码的问题
            // String disposition  = "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(filename, "utf-8");
            // response.setHeader("Content-Disposition", disposition);
            // 将输出流信息发送至客户端
            ServletOutputStream out = response.getOutputStream();

            // 读取并发送
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0){
                out.write(bytes,0,len);
            }

            inputStream.close();
            out.close();
            System.out.println("获取数据流");
            FileUtils.deleteAll(tempFullPath);
            System.out.println("已经删除临时文件");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    /**
     * 将用户选中(多个)文件夹或文件打包下载
     * @param response
     * @param selectedListStr
     * @param currentPathStr
     * @throws UnsupportedEncodingException
     */
    @RequiresAuthentication
    @GetMapping("/selected-file")
    public void packageSelectedDownload(
            HttpServletResponse response,
            @RequestParam(value = "selectedList") String selectedListStr,
            @RequestParam(value = "currentPath") String currentPathStr
            ) throws UnsupportedEncodingException {
        // 如果没有选中就不执行下面的语句了
        if (ObjectUtils.isEmpty(selectedListStr) || ObjectUtils.isEmpty(currentPathStr)){
            System.out.println("--------------------");
            System.out.println(selectedListStr);
            System.out.println(currentPathStr);
            System.out.println("--------------------");
            return;
        }

        String decodeStr = URLDecoder.decode(selectedListStr, "UTF-8");
        // 获取解码后的用户要打包下载的文件
        String[] selectedList = decodeStr.split(",");
        // 获取解码后的目录文件夹路径
        String currentPath = URLDecoder.decode(currentPathStr,"UTF-8");

        AccountProfile loginUser = UserUtils.getLoginUser();
        String id = loginUser.getId().toString();
        // 要下载的文件目标目录
        String targetFullPath = rootPath+CloudStorage+"/"+id;
//        String targetFullPath = rootPath+CloudStorage+"/"+currentPath+"/"+id;
        // zip临时存放目录
        String tempFullPath = rootPath+PackageTemp+"/"+id+"/";
        // 临时zip文件路径
        String zipFilePath = "";
        List<String> downloadFilePath = new ArrayList<>();
        for (String s : selectedList) {
            System.out.println("目标文件："+targetFullPath+s);
            downloadFilePath.add(targetFullPath+s);
        }
        try {
           zipFilePath = FileUtils.generateFileSelected(downloadFilePath,tempFullPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("zip文件位置: "+zipFilePath);
        // 把zip文件返回给前端下载

        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()){
            throw new RuntimeException("zip文件创建失败");
        }

        try {
            FileInputStream inputStream = new FileInputStream(zipFile);
            response.reset();
            response.setCharacterEncoding("utf-8");
            // 设置相关格式
            response.setContentType("application/force-download");
            String filename = zipFile.getName();
            // 解决下载文件名乱码的问题
            // String disposition  = "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(filename, "utf-8");
            // response.setHeader("Content-Disposition", disposition);
            // 将输出流信息发送至客户端
            ServletOutputStream out = response.getOutputStream();



            // 读取并发送
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0){
                out.write(bytes,0,len);
            }

            inputStream.close();
            out.close();
            System.out.println("获取数据流");
            FileUtils.deleteAll(tempFullPath);
            System.out.println("已经删除临时文件");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
