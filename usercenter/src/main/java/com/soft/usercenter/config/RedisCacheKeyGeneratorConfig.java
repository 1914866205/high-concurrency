package com.soft.usercenter.config;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.codec.digest.DigestUtils;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName V.java
 * @Description TODO
 * @createTime 2021年03月25日 10:58:00
 */
@Configuration
public class RedisCacheKeyGeneratorConfig implements KeyGenerator {

    // 该值是 keyGenerator 方法的方法名称，如果Bean 指定了名称，则使用指定的名称
    public static final String DEFAULT_KEY_GENERATOR = "keyGenerator";

    // 定义缓存区，缓存区可以在配置时指定不同的过期时间，作为防止缓存雪崩的一个保护措施
    public static final String COMMON = "COMMON";

    @Override
    public Object generate(Object targetClass, Method method, Object... params) {
        // 这里可用HashMap
        HashMap<String, Object> container = new HashMap<>(10);
        Class<?> targetClassClass = targetClass.getClass();
        // 类地址
        container.put("class", targetClassClass.toGenericString());
        // 方法名称
        container.put("methodName", method.getName());
        // 包名称
        container.put("package", targetClassClass.getPackage());
        // 参数列表
        for (int i = 0; i < params.length; i++) {
            container.put(String.valueOf(i), params[i]);
        }
        // 转为JSON字符串
        String jsonString = JSON.toJSONString(container);
        // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
        return DigestUtils.sha256Hex(jsonString);
    }

    // 在 CacheConfig 中定义
    @Bean
    KeyGenerator keyGenerator() {
        return new RedisCacheKeyGeneratorConfig();
    }
}
