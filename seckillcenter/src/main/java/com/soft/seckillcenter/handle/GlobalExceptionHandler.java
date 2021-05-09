package com.soft.seckillcenter.handle;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.common.ResultCode;
import com.soft.seckillcenter.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 全局异常处理
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/
//@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常的处理，统一在这里捕获返回JSON格式的友好提示
     *
     * @param exception
     * @return ResponseResult
     */
    @ExceptionHandler(value = {JwtException.class})
    @ResponseBody
    public ResponseResult sendError(JwtException exception) {
        log.error(exception.getMessage());
        return ResponseResult.failure(exception.getResultCode());
    }


    /**
     * InvalidClaimException异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {InvalidClaimException.class})
    @ResponseBody
    public ResponseResult sendError(InvalidClaimException exception) {
        log.error(exception.getMessage());
        return ResponseResult.failure(ResultCode.USER_TOKEN_EXPIRES);
    }


    /**
     * NPE异常的处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseBody
    public ResponseResult sendError(NullPointerException exception) {
        log.error(exception.getMessage());
        return ResponseResult.failure(ResultCode.RESULT_CODE_DATA_NONE);
    }
}
