package com.soft.seckillcenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ResponseResult.java
 * @Description TODO
 * @createTime 2021年03月25日 10:53:00
 */
@Data
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;
    private String msg;
    private Object data;

    private ResponseResult() {
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult success() {
        ResponseResult result = new ResponseResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    //给指定代码
    public static ResponseResult success(ResultCode resultCode) {
        ResponseResult result = new ResponseResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static ResponseResult success(Object data) {
        ResponseResult result = new ResponseResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    //给指定代码
    public static ResponseResult failure(ResultCode resultCode) {
        ResponseResult result = new ResponseResult();
        result.setResultCode(resultCode);
        return result;
    }

    //给指定代码，返回指定内容
    public static ResponseResult failure(ResultCode resultCode, Object data) {
        ResponseResult result = new ResponseResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }


    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }


}
