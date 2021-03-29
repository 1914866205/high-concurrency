package com.soft.usercenter.service;

import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.model.dto.SmsPhoneDto;
import com.soft.usercenter.model.dto.VerifyPhoneDto;

import java.util.Map;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/26 8:24
 * @Version 1.0
 **/
public interface SendSmsService {
    //发送验证码
    boolean send(String phoneNum, String templateCode, Map<String, Object> code);

    //校验验证码
    boolean verify(VerifyPhoneDto verifyPhone);

    /**
     * 发送验证码
     *
     * @param smsPhoneDto
     * @return
     */
    ResponseResult code(SmsPhoneDto smsPhoneDto);
}
