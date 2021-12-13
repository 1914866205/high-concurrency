package com.soft.usercenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 校验手机号
 * @Author 涛涛
 * @Date 2020/5/27 8:27
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPhoneDto {
    //手机号
    private String phoneNumber;
    //验证码
    private String verifyCode;

}
