package com.soft.usercenter.exception;


import com.soft.usercenter.common.ResultCode;

/**
 * @Description 自定义异常
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/
public class JwtException extends RuntimeException {
    protected ResultCode resultCode;

    public JwtException(String msg, ResultCode resultCode) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
