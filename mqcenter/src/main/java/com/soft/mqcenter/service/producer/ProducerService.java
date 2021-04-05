package com.soft.mqcenter.service.producer;

import com.soft.mqcenter.model.dto.OrderDto;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ProducerService.java
 * @Description TODO
 * @createTime 2021年04月05日 14:14:00
 */
public interface ProducerService {
    void sendMessage(OrderDto orderDto);
}
