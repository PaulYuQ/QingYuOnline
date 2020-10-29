package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 小强子大大
 */
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {

    @Autowired
    FileSystemService fileSystemService;

    /**
     * 上传文件接口
     *
     * @param multipartFile 文件本身
     * @param filetag       文件标签
     * @param businesskey   文件标识
     * @param metadata      文件元信息
     * @return
     */
    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata) {
        return fileSystemService.upload(multipartFile, filetag, businesskey, metadata);
    }
}
