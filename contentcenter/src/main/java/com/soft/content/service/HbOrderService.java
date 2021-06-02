package com.soft.content.service;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.model.dto.SecResultDto;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:49:00
 */
public interface HbOrderService {
    /**
     * 添加订单
     * @param hbOrderDto
     * @return
     */
    ResponseResult addOrder(OrderDto hbOrderDto);

    /**
     * 取消订单
     * @param hBorderId
     * @return
     */
    ResponseResult celOrder(String hBorderId);

    /**
     * 支付订单
     * @param hBorderId
     * @return
     */
    ResponseResult payOrder(String hBorderId);

    /**
     * 查询该用户所有订单
     * @param pkUserId
     * @return
     */
    ResponseResult findUserAllOrder(String pkUserId);

    /**
     * 秒杀订单，进行分批发送
     * @param orderDto
     * @return
     */
    ResponseResult seckOrder(OrderDto orderDto);

    ResponseResult findSecKillUserOrder(SecResultDto secResultDto);

    /**
     * 批量添加订单
     * @param queue
     */
    void batchAddOrder(LinkedBlockingQueue<OrderDto> queue);

    /**
     * 修改订单状态
     */
    ResponseResult commentOrder(String hbOrderId, int state);
//
//    /**
//     * 把成功的订单请求发送给消息队列
//     * @param orderDto
//     */
//    void secKill(OrderDto orderDto);
    /**
     * 修改订单状态
     */
    ResponseResult commentOrder(String hbOrderId, int state);
}
