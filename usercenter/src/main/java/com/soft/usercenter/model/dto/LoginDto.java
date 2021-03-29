package com.soft.usercenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName LoginDto.java
 * @Description TODO
 * @createTime 2021年03月29日 09:06:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    //账号
    private String username;
    //密码
    private String password;
}
