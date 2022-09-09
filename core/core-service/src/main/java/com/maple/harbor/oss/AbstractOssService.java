package com.maple.harbor.oss;

import cn.hutool.core.lang.Assert;
import com.maple.harbor.exception.BizException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Setter
public abstract class AbstractOssService implements OssService {

    @Autowired
    protected OssVersionTemplate ossTemplate;

    private String bucket;

    @PostConstruct
    public void initBucket() {
        String bucket = getBucketName();
        Assert.notBlank(bucket, () -> BizException.commonFail("桶名称不能为空!"));
        synchronized (this) {
            if (!ossTemplate.bucketExists(bucket)) {
                ossTemplate.createBucket(bucket, true);
            }
        }
        this.bucket = bucket;
    }

    abstract protected String getBucketName();

}
