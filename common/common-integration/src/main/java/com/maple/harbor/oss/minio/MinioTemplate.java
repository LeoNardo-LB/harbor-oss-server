package com.maple.harbor.oss.minio;

import com.maple.harbor.log.AroundLog;
import com.maple.harbor.oss.OssVersionTemplate;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * minio 开发指导 api: https://minio-java.min.io/io/minio/MinioClient.html
 */

@Component("MinioTemplate")
@Slf4j
public class MinioTemplate implements OssVersionTemplate {

    /**
     * ossClient
     */
    @Autowired
    private MinioClient minioClient;

    @Override
    public ObjectWriteResponse putObject(String bucket, String filename, InputStream is, Map<String,String> userMetadata) {
        try {
            return minioClient.putObject(PutObjectArgs.builder()
                                                 .bucket(bucket)
                                                 .object(filename)
                                                 .stream(is, is.available(), -1)
                                                 .userMetadata(userMetadata)
                                                 .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @AroundLog
    @Override
    public GetObjectResponse getObject(String bucket, String filename) {
        return getObject(bucket, filename, null);
    }

    @Override
    public GetObjectResponse getObject(String bucket, String filename, String versionId) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                                                 .bucket(bucket)
                                                 .object(filename)
                                                 .versionId(versionId)
                                                 .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StatObjectResponse getObjectMetadata(String bucket, String filename) {
        return getObjectMetadata(bucket, filename, null);
    }

    @Override
    public StatObjectResponse getObjectMetadata(String bucket, String filename, String versionId) {
        try {
            return minioClient.statObject(StatObjectArgs.builder()
                                                  .bucket(bucket)
                                                  .object(filename)
                                                  .versionId(versionId)
                                                  .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getObjectUrl(String bucket, String filename, Integer expire, TimeUnit timeUnit) {
        return getObjectUrl(bucket, filename, expire, timeUnit, null);
    }

    @Override
    public String getObjectUrl(String bucket, String filename, Integer expire, TimeUnit timeUnit, String versionId) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                                                             .bucket(bucket)
                                                             .object(filename)
                                                             .expiry(expire, timeUnit)
                                                             .versionId(versionId)
                                                             .method(Method.GET)
                                                             .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Result<Item>> getObjectList(String bucket, String filePrefix, String marker, Integer max) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                                               .bucket(bucket)
                                               .prefix(filePrefix)
                                               .keyMarker(marker)
                                               .recursive(true)
                                               .maxKeys(max)
                                               .build());
    }

    @Override
    public Boolean deleteObject(String bucket, String filename) {
        return deleteObject(bucket, filename, null);
    }

    @Override
    public Boolean deleteObject(String bucket, String filename, String versionId) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                                             .bucket(bucket)
                                             .object(filename)
                                             .versionId(versionId)
                                             .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Iterable<Result<DeleteError>> deleteObjects(String bucket, Set<String> deleteObjects) {
        return minioClient.removeObjects(RemoveObjectsArgs.builder()
                                                 .bucket(bucket)
                                                 .objects(deleteObjects.stream().map(DeleteObject::new).collect(Collectors.toSet()))
                                                 .build());
    }

    @Override
    public List<Bucket> listAllBucket() {
        try {
            return minioClient.listBuckets();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean bucketExists(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                                                    .bucket(bucket)
                                                    .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createBucket(String bucket, boolean versionController) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                                           .bucket(bucket)
                                           .objectLock(versionController)
                                           .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBucket(String bucket) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                                             .bucket(bucket)
                                             .build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

}
