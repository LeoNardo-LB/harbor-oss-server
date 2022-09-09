package com.maple.harbor.result;

import lombok.Data;

@Data
public class BaseResult <T> {

    /**
     * 是否成功
     */
    protected boolean success;

    /**
     * 状态码
     */
    protected int code;

    /**
     * 数据
     */
    protected T data;

    /**
     * 信息
     */
    protected String message;


}
