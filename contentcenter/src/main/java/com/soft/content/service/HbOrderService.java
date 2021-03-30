package com.soft.content.service;

import com.soft.content.common.ResponseResult;
import com.soft.content.common.ResultCode;
import com.soft.content.model.dto.OrderDto;

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
}