package com.soft.content.config;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserCenterFeignConfiguration.java
 * @Description TODO
 * @createTime 2020年09月30日 09:40:00
 */

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 先写一个自定义的类，返回feign的日志级别，
 * 头部不要加@Configuration注解，
 * 否则会父子上下文重叠，必须挪到外面的包
 *
 * @author nitaotao
 */
public class UserCenterFeignConfiguration {

    @Bean
    public Logger.Level level(){
        //让Feign打印所有请求细节
        return Logger.Level.FULL;
    }
}
