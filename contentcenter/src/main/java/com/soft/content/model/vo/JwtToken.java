package com.soft.content.model.vo;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description 实现shiro的Authenication接口的类JwtToken
 * @Author 涛涛
 * @Date 2020/5/24 10:27
 * @Version 1.0
 **/
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
