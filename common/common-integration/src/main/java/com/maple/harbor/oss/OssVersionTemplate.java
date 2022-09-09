package com.maple.harbor.oss;

import io.minio.GetObjectResponse;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public interface OssVersionTemplate extends OssTemplate{

    /**
     * 获取Object
     * @param bucket 桶名称
     * @param filename 文件名
     * @param versionId 版本号
     * @return
     */
    GetObjectResponse getObject(String bucket, String filename, String versionId)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
                           InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 获取Url
     * @param bucket 桶名称
     * @param filename 文件名
     * @param versionId 版本号
     * @return
     */
    String getObjectUrl(String bucket, String filename, Integer expire, TimeUnit timeUnit, String versionId);

    /**
     * 获取对象元信息
     * @param bucket 桶名称
     * @param filename 文件名
     * @param versionId 版本号
     * @return
     */
    StatObjectResponse getObjectMetadata(String bucket, String filename, String versionId)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
                           InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 删除某个对象
     * @param bucket 桶名称
     * @param filename 文件名
     * @param versionId 版本号
     * @return
     */
    Boolean deleteObject(String bucket, String filename, String versionId)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
                           InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
