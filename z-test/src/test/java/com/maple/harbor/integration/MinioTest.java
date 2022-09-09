package com.maple.harbor.integration;

import com.maple.harbor.ServiceTestBase;
import com.maple.harbor.oss.minio.MinioTemplate;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioTest extends ServiceTestBase {

    @Autowired
    private MinioClient minioClient;

    /**
     * 测试相同的文件上传到不同的bucket
     * 结论:
     * 1. 只要文件相同, 就算上传到不同的bucket中, etag也相同
     * 2. 相同bucket中 etag相同的文件
     */
    @Test
    public void test_putObject_sameFile() throws IOException {

        try {
            String bucket1 = "test-bucket-1";
            String bucket2 = "test-bucket-2";

            synchronized (this) {
                if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket1).build())) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket1).build());
                }
                if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket2).build())) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket2).build());
                }
            }

            FileInputStream fis = new FileInputStream(new File("D:\\图片\\wallhaven-572x71.jpg"));
            ObjectWriteResponse r1 = minioClient.putObject(PutObjectArgs.builder().stream(fis, fis.available(), 0).bucket(bucket1)
                                                                   .object("folder123/3putObject_p1.jpg").build());
            System.out.println("r1 = " + r1);
            fis.close();

            FileInputStream fis2 = new FileInputStream(new File("D:\\图片\\wallhaven-572x71.jpg"));
            ObjectWriteResponse r2 = minioClient.putObject(PutObjectArgs.builder().stream(fis2, fis2.available(), 0).bucket(bucket2)
                                                                   .object("folder123/3putObject_p2.jpg").build());
            System.out.println("r2 = " + r2);
            fis2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试不同文件上传到同一个bucket中, 且同名
     * 结论: 后者会覆盖掉前者
     */
    @Test
    public void test_putOjbect_difFile() throws Exception {

        FileInputStream fis = new FileInputStream(new File("D:\\图片\\wallhaven-572x71.jpg"));
        FileInputStream fis2 = new FileInputStream(new File("D:\\图片\\wallhaven-rdm6km.png"));

        String bucket = "test-bucket";

        synchronized (this) {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        }

        ObjectWriteResponse r1 = minioClient.putObject(PutObjectArgs.builder().stream(fis, fis.available(), 0).bucket(bucket)
                                                               .object("pic.jpg").build());
        System.out.println("r1 = " + r1);
        fis.close();
        ObjectWriteResponse r2 = minioClient.putObject(PutObjectArgs.builder().stream(fis2, fis2.available(), 0).bucket(bucket)
                                                               .object("pic.jpg").build());
        System.out.println("r2 = " + r2);
        fis2.close();

    }

    /**
     * 测试获取文件的方式
     * 结论:
     * 1. listObjects 可以控制是否递归获取
     * - 递归获取: 获取所有符合条件的文件(无论是否在文件夹中), 包含文件夹的前缀
     * - 非递归获取: 直接获取符合条件的文件, 如果这个文件是一个文件夹, 则没有etag, isDir属性为true
     * 2. getObject 必传bucket 与 object
     * - 对象不存在抛出异常：ErrorResponseException - The specified key does not exist
     * - 不传bucket 或 object 会抛出 IllegalArgumentException 异常
     * - 是否匹配etag, 不满足条件会抛出如下异常
     *      需要匹配 matchETag 异常: io.minio.errors.ErrorResponseException - At least one of the pre-conditions you specified did not hold
     *      不能匹配 notMatchETag 异常: io.minio.errors.ServerException - server failed with HTTP status code 304
     */
    @Test
    public void test_AquireObject() throws Exception {

        String bucket = "test-bucket-vc";

        printList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(false).build()));
        printList(minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(true).build()));

        try {
            System.out.println(minioClient.getObject(GetObjectArgs.builder()
                                                             .bucket(bucket)
                                                             .object("572x71.jpg")
                                                             .versionId("null")
                                                             .build()));
            // http code 304
            // System.out.println(minioClient.getObject(GetObjectArgs.builder()
            //                                                  .bucket(bucket)
            //                                                  .object("wallhaven-572x71.jpg")
            //                                                  .notMatchETag("65e49a1e7e0bf5a3fde55eecaa18899c\n")
            //                                                  .build()));

            // code = PreconditionFailed
            // System.out.println(minioClient.getObject(GetObjectArgs.builder()
            //                                                  .bucket(bucket)
            //                                                  .object("wallhaven-rdm6km.png")
            //                                                  .matchETag("12321-3\n")
            //                                                  .build()));

            // 对象不存在异常：ErrorResponseException - The specified key does not exist
            // GetObjectResponse noExistsObj1 = minioClient.getObject(GetObjectArgs.builder()
            //                                                                .bucket(bucket)
            //                                                                .object("noExistsObj")
            //                                                                .build());
        } catch (Exception e) {
            System.err.println(e.getClass() + " - " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Test
    public void test_versionController() throws Exception {
        String bucket = "test-bucket-vc";
        synchronized (this) {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                                               .objectLock(true)
                                               .bucket(bucket).build());
                // VersioningConfiguration configuration = minioClient.getBucketVersioning(GetBucketVersioningArgs.builder()
                //                                                                                 .bucket(bucket).build());
                // minioClient.setBucketVersioning(SetBucketVersioningArgs.builder()
                //                                         .bucket(bucket)
                //                                         .config(new VersioningConfiguration(Status.ENABLED, null))
                //                                         .build());
            }
        }
        FileInputStream fis = new FileInputStream(new File("D:\\图片\\572x71.jpg"));
        FileInputStream fis2 = new FileInputStream(new File("D:\\图片\\572x71.jpg"));
        // FileInputStream fis2 = new FileInputStream(new File("D:\\图片\\rdm6km.png"));

        ObjectWriteResponse r1 = minioClient.putObject(PutObjectArgs.builder()
                                                               .bucket(bucket)
                                                               .object("obj2.jpg")
                                                               .stream(fis, fis.available(), -1).build());
        System.out.println("r1 = " + r1);
        ObjectWriteResponse r2 = minioClient.putObject(PutObjectArgs.builder()
                                                               .bucket(bucket)
                                                               .object("obj2.jpg")
                                                               .stream(fis2, fis2.available(), -1).build());
        System.out.println("r2 = " + r2);

    }

    /**
     * 信息：如果对象不存在，则会抛出异常 ErrorResponseException - Object does not exist
     * @throws Exception
     */
    @Test
    public void test_objectState() throws Exception {
        String bucket = "test-bucket-1";
        StatObjectResponse statObject = minioClient.statObject(StatObjectArgs.builder()
                                                                       .bucket(bucket)
                                                                       .object("1k6ljv.jpg")
                                                                       .build());
        printOne(statObject);
        StatObjectResponse noExistsObj = null;
        try {
            noExistsObj = minioClient.statObject(StatObjectArgs.builder()
                                                         .bucket(bucket)
                                                         .object("noExistsObj")
                                                         .build());
            printOne(noExistsObj);
        } catch (Exception e) {
            System.out.println(e.getClass() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 打印list
     */
    private void printList(Iterable<Result<Item>> list) {
        System.out.println("===========[ 开始打印... ]===========");
        for (Result<Item> result : list) {
            try {
                Item item = result.get();
                printOne(item);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("===========[ 结束打印!!! ]===========");
        System.out.println();
    }

    /**
     * 打印属性
     */
    private static void printOne(Object obj) throws IllegalAccessException {
        Field[] allFields = FieldUtils.getAllFields(obj.getClass());
        StringBuilder builder = new StringBuilder();
        for (Field field : allFields) {
            field.setAccessible(true);
            Object value = FieldUtils.readField(field, obj);
            builder.append("[ ").append(field.getName()).append(" - ").append(value).append(" ] ");
        }
        System.out.println(builder.toString());
    }

    @Autowired
    private MinioTemplate minioTemplate;

    @Test
    public void test_minioTemplate(){

        GetObjectResponse object = minioTemplate.getObject("test-bucket-1", "1k6ljv.jpg");

    }

}
