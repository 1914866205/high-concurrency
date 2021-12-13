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
 * @ClassName seckillCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年04月02日 12:57:00
 */
@FeignClient(name = "secKill-center")
public interface SecKillCenterFeignClient {
    /**
     * 由于Jmeter测试并发量过大，至少一万
     * 而单机并发最多三千，所以要额外开一个服务
     * 忽略端口号，访问同一个接口而负载均衡
     * @param orderDto
     * @return
     */
    @PostMapping("order/secKill")
    ResponseResult secKill(@RequestBody OrderDto orderDto);

    /**
     * 批量分批发送
     * @param queue
     */
    @PostMapping("order/batchSecKill")
    void batchSeckill(@RequestBody LinkedBlockingQueue<OrderDto> queue);
}
