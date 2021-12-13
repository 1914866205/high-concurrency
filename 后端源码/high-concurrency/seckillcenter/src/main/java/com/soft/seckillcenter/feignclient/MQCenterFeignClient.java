package com.soft.seckillcenter.feignclient;

import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.model.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName MQCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年04月05日 15:08:00
 */
@FeignClient(name = "mq-center")
public interface MQCenterFeignClient {

    @PostMapping("messageToQueue")
    ResponseResult messageToQueue(@RequestBody OrderDto orderDto);
}
