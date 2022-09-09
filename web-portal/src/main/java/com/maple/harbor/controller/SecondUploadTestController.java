package com.maple.harbor.controller;

import com.maple.harbor.oss.OssService;
import com.maple.harbor.oss.impl.UniqueFileOssService;
import com.maple.harbor.result.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 秒传服务
 */
@RestController
@RequestMapping("secondUpload")
public class SecondUploadTestController {

    @Autowired
    @Qualifier("uniqueFileOssService")
    private OssService uniqueFileOssService;

    @PostMapping("upload")
    public MvcResult<Boolean> uploadFile(MultipartFile file){
        return MvcResult.success(uniqueFileOssService.putObject("test-second-upload", file));
    }

    @GetMapping("url")
    public MvcResult<String> getUrl(String originFilename){
        return MvcResult.success(uniqueFileOssService.getObjectUrl("test-second-upload", originFilename));
    }

}
