package com.maple.harbor.result;

import lombok.Data;

@Data
public class OssResult<T> extends BaseResult<T>{

    public static <T> OssResult<T> success(T data, String message) {
        return new OssResult(true, data, null);
    }

    public static <T> OssResult<T> fail(String message) {
        return new OssResult(false, null, message);
    }

    private OssResult(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

}
