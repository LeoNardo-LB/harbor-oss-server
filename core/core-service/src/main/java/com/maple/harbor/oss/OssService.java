package com.maple.harbor.oss;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface OssService {

    /**
     * 上传文件
     * @param abilityBasedFolder 基于能力的文件夹名称
     * @param multipartFile      上传文件的multipartFile
     * @return 上传成功返回true, 否则返回false
     */
    Boolean putObject(String abilityBasedFolder, MultipartFile multipartFile);

    OutputStream getObject();

    String getObjectUrl(String abilityBasedFolder, String originFilename);

    String getObjectMatedata();

    List getObjectList();

    Boolean deleteObject();

}
