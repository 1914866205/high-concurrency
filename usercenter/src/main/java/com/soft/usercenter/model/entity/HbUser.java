package com.soft.usercenter.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUser.java
 * @Description TODO
 * @createTime 2021年03月25日 12:05:00
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HbUser {
    /**
     * 主键ID
     */
    @Id
    private String pkUserId;

    /**
     * 用户名
     */
    @Column(nullable = false, length = 20)
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false, length = 20)
    private String password;

    /**
     * 手机号
     */
    @Column(nullable = false, length = 21, unique = true)
    private String phone;

    /**
     * 性别
     * 1 男
     * 2 女
     */
    @Column(nullable = false, length = 2)
    private int sex;

    /**
     * 用户头像
     */
    @Column(nullable = false)
    private String avatar;


    /**
     * 用户地址
     */
    @Column(nullable = false)
    private String address;

    /**
     * 用户邮箱
     */
    @Column(nullable = false)
    private String email;


    /**
     * 用户加密盐
     */
    @Column(nullable = false)
    private String sale;

    /**
     * 用户昵称
     */
    @Column(nullable = false, length = 50)
    private String nickname;


    /**
     * 用户余额
     */
    @Column(nullable = false, length = 20)
    private Double money;


    /**
     * 生日
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp birthday;


    /**
     * 创建时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;

    /**
     * 激活状态
     * 0 未激活
     * 1 已激活
     */
    @Column(nullable = false, length = 2)
    private int status;
}

