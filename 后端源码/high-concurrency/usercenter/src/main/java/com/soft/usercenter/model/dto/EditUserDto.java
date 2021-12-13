package com.soft.usercenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName EditUserDto.java
 * @Description TODO
 * @createTime 2021年03月29日 13:11:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDto {
    private String pkUserId;
    private String username;
    private String password;
    private String phone;
    /**
     * 手机验证码
     */
    private String code;
    /**
     * 1男 2女
     */
    private int sex;
    private String avatar;
    private String address;
    private String email;
    private String nickname;
    private Double money;
}

