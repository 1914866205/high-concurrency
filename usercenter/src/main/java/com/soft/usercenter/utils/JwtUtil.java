package com.soft.usercenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Description 用来进行签名和校验Token
 * @Author 涛涛
 * @Date 2020/5/24 8:55
 * @Version 1.0
 **/
@Slf4j
public class JwtUtil {
    /**
     * JWT验证过期时间 EXPIRE_TIME 分钟
     */
    private static final long EXPIRE_TIME = 15 * 24 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token       秘钥
     * @param userAccount 用户名
     * @param password    密码
     * @return
     */
    public static boolean verify(String token, String userAccount, String password) {
        try {

            //根据密码生成JWT校验器
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userAccount", userAccount)
                    .build();
            //校验Token
            DecodedJWT jwt = verifier.verify(token);
            log.info("登录验证成功");
            return true;
        } catch (UnsupportedEncodingException e) {
            log.info("登录验证失败");
            return false;
        }
    }

    /**
     * 获得token中的用户名，无需password也能解密
     *
     * @param token
     * @return
     */
    public static String getUserAccount(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userAccount").asString();
    }

    /**
     * 生成token签名EXPIRE_TIME 分钟后过期
     *
     * @param username 用户名
     * @param password    用户密码
     * @return 加密的token
     * @throws UnsupportedEncodingException
     */
    public static String sign(String username, String password) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(Md5Util.getMd5(password, true, 32));
        //附带user信息
        return JWT.create().withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 生成token签名EXPIRE_TIME 分钟后过期
     *用于验证码生成token
     * @param userAccount 用户名
     * @param password    用户密码
     * @return 加密的token
     * @throws UnsupportedEncodingException
     */
    public static String sign2(String userAccount, String password) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(password);
        //附带userAccount信息
        return JWT.create().withClaim("userAccount", userAccount)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
