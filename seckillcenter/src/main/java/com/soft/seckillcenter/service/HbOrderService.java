package com.soft.seckillcenter.service;


import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.model.dto.OrderDto;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:49:00
 */
public interface HbOrderService {

    /**
     * 把成功的订单请求发送给消息队列
     * @param orderDto
     */
    void secKill(OrderDto orderDto);

    ResponseResult toSecKill(OrderDto orderDto);
}
