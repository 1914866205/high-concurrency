package com.soft.content.feignclient;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.LinkedBlockingQueue;

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


    @PostMapping("messageBatchToQueue")
    void messageBatchToQueue(LinkedBlockingQueue<OrderDto> queue);
}
