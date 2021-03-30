package com.soft.content.config;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName Globalfeignconfiguration.java
 * @Description TODO
 * @createTime 2020年09月29日 20:38:00
 */

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * feign的配置类
 * 这个类不要@Configuration注解，否则必须挪到@ComponentScan能扫描的包外
 */
public class GlobalFeignConfiguration {
    @Bean
    public Logger.Level level() {
        //让feign打印所有请求的细节
        return Logger.Level.FULL;
    }

}
