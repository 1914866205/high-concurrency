package com.soft.content.feignclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName TokenInterceptor.java
 * @Description TODO
 * @createTime 2020年10月13日 16:40:00
 */
public class TokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1. 获取到token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");
        // 2. 将token传递
        if (StringUtils.isNotBlank(token)) {
            requestTemplate.header("X-Token", token);
        }
    }
}
