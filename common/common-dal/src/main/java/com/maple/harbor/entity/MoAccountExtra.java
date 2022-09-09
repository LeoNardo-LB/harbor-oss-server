package com.maple.harbor.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 账号详细信息表(MoAccountExtra)表实体类
 * @author makejava
 * @since 2022-08-30 23:23:26
 */
@SuppressWarnings("serial")
public class MoAccountExtra extends Model<MoAccountExtra> {
    //主键id
    private Long    id;
    //创建时间
    private Date    createTime;
    //修改时间
    private Date    modifyTime;
    //用户索引, 关联：mo_account#account_id
    private Long    accountId;
    //性别
    private Integer gender;
    //年龄
    private Integer age;
    //地区 格式：城市-省份-城市
    private String  area;
    //生日
    private Date    birthday;
    //qq 号
    private String  qqCode;
    //qq 授权信息
    private String  qqAuthorizeInfo;
    //微信号
    private String  wechatCode;
    //微信授权信息
    private String  wechatAuthorizeInfo;
    //支付宝账号
    private String  alipayCode;
    //支付宝授权信息
    private String  alipayAuthorizeInfo;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQqCode() {
        return qqCode;
    }

    public void setQqCode(String qqCode) {
        this.qqCode = qqCode;
    }

    public String getQqAuthorizeInfo() {
        return qqAuthorizeInfo;
    }

    public void setQqAuthorizeInfo(String qqAuthorizeInfo) {
        this.qqAuthorizeInfo = qqAuthorizeInfo;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getWechatAuthorizeInfo() {
        return wechatAuthorizeInfo;
    }

    public void setWechatAuthorizeInfo(String wechatAuthorizeInfo) {
        this.wechatAuthorizeInfo = wechatAuthorizeInfo;
    }

    public String getAlipayCode() {
        return alipayCode;
    }

    public void setAlipayCode(String alipayCode) {
        this.alipayCode = alipayCode;
    }

    public String getAlipayAuthorizeInfo() {
        return alipayAuthorizeInfo;
    }

    public void setAlipayAuthorizeInfo(String alipayAuthorizeInfo) {
        this.alipayAuthorizeInfo = alipayAuthorizeInfo;
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

