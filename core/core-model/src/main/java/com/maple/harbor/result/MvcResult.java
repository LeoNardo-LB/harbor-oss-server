package com.maple.harbor.result;

import lombok.Data;

@Data
public class MvcResult<T> {

    private Boolean success;

    private int code;

    private String message;

    private T data;

    public static <T> MvcResult<T> success(T data){
        MvcResult<T> result = new MvcResult<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> MvcResult<T> fail(String message){
        MvcResult<T> result = new MvcResult<>();
        result.setSuccess(false);
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

}
