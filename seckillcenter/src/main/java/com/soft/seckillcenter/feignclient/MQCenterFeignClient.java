package com.soft.seckillcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName MQCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年04月05日 15:08:00
 */
@FeignClient(name = "mq-center")
public interface MQCenterFeignClient {

}
