package com.soft.usercenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * @Description 发送验证码
 * @Author 涛涛
 * @Date 2020/5/27 9:00
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsPhoneDto {
    //手机号
    @Pattern(regexp = "^[1](([3|5|8][\\\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\\\d]{8}$", message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;
}
