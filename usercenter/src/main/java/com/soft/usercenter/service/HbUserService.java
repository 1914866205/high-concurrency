package com.soft.usercenter.service;

import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.model.dto.LoginDto;
import com.soft.usercenter.model.dto.VerifyPhoneDto;

import java.io.UnsupportedEncodingException;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUserService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:48:00
 */
public interface HbUserService {


    /**
     * 账号密码登录
     *
     * @param loginDto
     * @return
     */
    ResponseResult login(LoginDto loginDto) throws UnsupportedEncodingException;

    /**
     * 手机号验证码登录
     * @param verifyPhone
     * @return
     */
    ResponseResult loginByPhone(VerifyPhoneDto verifyPhone) throws UnsupportedEncodingException;
}
