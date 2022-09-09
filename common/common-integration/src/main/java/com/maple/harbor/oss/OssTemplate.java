package com.maple.harbor.oss;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;
import io.minio.Result;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface OssTemplate {

    ObjectWriteResponse putObject(String bucket, String filename, InputStream is, Map<String, String> userMetadata);

    GetObjectResponse getObject(String bucket, String filename);

    StatObjectResponse getObjectMetadata(String bucket, String filename);

    String getObjectUrl(String bucket, String filename, Integer expire, TimeUnit timeUnit);

    Iterable<Result<Item>> getObjectList(String bucket, String filePrefix, String marker, Integer max);

    Boolean deleteObject(String bucket, String filename);

    Iterable<Result<DeleteError>> deleteObjects(String bucket, Set<String> deleteObjects);

    List<Bucket> listAllBucket();

    boolean bucketExists(String bucket);

    void createBucket(String bucket, boolean versionController);

    void deleteBucket(String bucket);

}
