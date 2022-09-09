package com.maple.harbor.result;

import lombok.Data;

@Data
public class MvcResult<T> extends BaseResult<T>{

    private int code;


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
