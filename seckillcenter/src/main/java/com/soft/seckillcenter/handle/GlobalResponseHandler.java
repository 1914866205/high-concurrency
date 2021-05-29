package com.soft.seckillcenter.handle;

/**
 * @Description 全局统一响应处理
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/

import com.soft.seckillcenter.common.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


//@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {


    /**
     * 此处如果返回false , 则不执行当前Advice的业务
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 处理响应的具体方法
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResponseResult) {
            return body;
        } else {
            return ResponseResult.success(body);
        }
    }
}