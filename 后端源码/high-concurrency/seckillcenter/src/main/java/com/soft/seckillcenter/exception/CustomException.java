package com.soft.seckillcenter.exception;


import com.soft.seckillcenter.common.ResultCode;

/**
 * @Description 用户自定义异常
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/
public class CustomException extends RuntimeException {
    protected ResultCode resultCode;

    public CustomException(String msg, ResultCode resultCode) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
