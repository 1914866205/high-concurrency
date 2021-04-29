package com.soft.mqcenter.feignclient;


import com.soft.mqcenter.common.ResponseResult;
import com.soft.mqcenter.model.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ContentCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年4月5日 14:00:03
 */
@FeignClient(name = "content-center")
public interface ContentCenterFeignClient {


    /**
     * 返回调用内容中心添加订单接口
     *
     * @param orderDto
     * @return
     */
    @PostMapping("order/addOrder")
    ResponseResult addOrder(@RequestBody OrderDto orderDto);

    /**
     * 返回调用内容中心获取所有商品Id接口
     * @return
     */
    @GetMapping("goods/findAllGoodId")
    List<String> getAllGoodId();

    /**
     * 返回调用内容中心批量添加订单接口
     *
     * @param queue
     * @return
     */
    @PostMapping("order/batchAddOrder")
    void batchAddOrder(LinkedBlockingQueue<OrderDto> queue);
}

