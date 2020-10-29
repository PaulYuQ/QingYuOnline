package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 小强子大大
 */
@Api(value = "文件管理接口", description = "文件管理接口，提供文件的增、删、改、查")
public interface FileSystemControllerApi {

    /**
     * 上传文件接口
     * @param multipartFile 文件本身
     * @param filetag 文件标签
     * @param businesskey 文件标识
     * @param metadata 文件元信息
     * @return
     */
    @ApiOperation("上传文件接口")
    UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata);
}
