package com.maple.harbor.exception;

public class BizException extends RuntimeException {

    private int errorCode;

    public BizException(Exception e) {
        super(e);
    }

    /**
     * 错误码 + 错误信息
     * @param errorCode
     * @param message
     */
    private BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 通用错误
     * @param message
     * @return
     */
    public static BizException commonFail(String message) {
        return new BizException(1002, message);
    }

}
