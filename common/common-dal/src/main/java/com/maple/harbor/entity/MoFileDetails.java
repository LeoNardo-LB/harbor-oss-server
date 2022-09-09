package com.maple.harbor.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 文件信息存储表(MoFileDetails)表实体类
 * @author makejava
 * @since 2022-08-30 23:35:23
 */
@SuppressWarnings("serial")
public class MoFileDetails extends Model<MoFileDetails> {
    //主键id
    private Long   id;
    //创建时间
    private Date   createTime;
    //修改时间
    private Date   modifyTime;
    //文件的bucket名称
    private String bucketName;
    //文件版本信息
    private String fileVersionId;
    //文件大小(KB)
    private Long   fileSize;
    //状态
    private String status;

    private String fileMd5;
    //文件在oss上存储的路径, 格式为: 功能/md5编码
    private String filePath;
    //文件的etag信息
    private String fileEtag;
    //oss生成的文件访问链接
    private String generateLink;
    //文件到期时间
    private Date   linkDeadline;

    private String contentType;

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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileEtag() {
        return fileEtag;
    }

    public void setFileEtag(String fileEtag) {
        this.fileEtag = fileEtag;
    }

    public String getGenerateLink() {
        return generateLink;
    }

    public void setGenerateLink(String generateLink) {
        this.generateLink = generateLink;
    }

    public Date getLinkDeadline() {
        return linkDeadline;
    }

    public void setLinkDeadline(Date linkDeadline) {
        this.linkDeadline = linkDeadline;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

