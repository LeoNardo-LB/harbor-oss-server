package com.maple.harbor.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (MoAccountFileRelation)表实体类
 * @author makejava
 * @since 2022-08-30 23:23:26
 */
@SuppressWarnings("serial")
public class MoAccountFileRelation extends Model<MoAccountFileRelation> {

    private Long id;

    private Date createTime;

    private Date   modifyTime;
    //账号id, 关联 mo_account_basic#account_id
    private Long   accountId;
    //文件id, 关联 mo_file_details#file_id
    private Long   fileId;
    //修改者的账号id
    private Long   modifierId;
    //此文件的短链
    private String shortLink;

    private String status;
    //存储在oss中的文件路径
    private String ossFilePath;

    private String userDefineFilename;

    private String originFilename;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOssFilePath() {
        return ossFilePath;
    }

    public void setOssFilePath(String ossFilePath) {
        this.ossFilePath = ossFilePath;
    }

    public String getUserDefineFilename() {
        return userDefineFilename;
    }

    public void setUserDefineFilename(String userDefineFilename) {
        this.userDefineFilename = userDefineFilename;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public void setOriginFilename(String originFilename) {
        this.originFilename = originFilename;
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

