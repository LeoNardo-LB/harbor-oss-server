package com.maple.harbor.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 文件信息存储表(MoFileDetails)表实体类
 * @author makejava
 * @since 2022-08-27 14:11:36
 */
@SuppressWarnings("serial")
public class MoFileDetails extends Model<MoFileDetails> {
    //主键id
    private Long   id;
    //创建时间
    private Date   createTime;
    //修改时间
    private Date   modifyTime;
    //文件id
    private Long   fileId;
    //文件的bucket名称
    private String bucketName;
    //文件在oss上存储的路径, 格式为: 功能/md5编码
    private String ossPath;
    //文件的etag信息
    private String ossEtag;
    //文件版本信息
    private String fileVersionId;
    //文件大小(KB)
    private Long   fileSize;
    //oss生成的文件访问链接
    private String ossGenerateLink;
    //文件到期时间
    private Date   ossDeadline;
    //状态
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public String getOssEtag() {
        return ossEtag;
    }

    public void setOssEtag(String ossEtag) {
        this.ossEtag = ossEtag;
    }

    public String getFileVersionId() {
        return fileVersionId;
    }

    public void setFileVersionId(String fileVersionId) {
        this.fileVersionId = fileVersionId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getOssGenerateLink() {
        return ossGenerateLink;
    }

    public void setOssGenerateLink(String ossGenerateLink) {
        this.ossGenerateLink = ossGenerateLink;
    }

    public Date getOssDeadline() {
        return ossDeadline;
    }

    public void setOssDeadline(Date ossDeadline) {
        this.ossDeadline = ossDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}

