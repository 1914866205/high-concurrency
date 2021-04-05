package com.soft.mqcenter.service.producer.impl;

import com.alibaba.fastjson.JSON;
import com.soft.mqcenter.common.Constants;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.producer.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ProduceServiceImpl.java
 * @Description redis消息队列的生产者，负责消息的发送
 * @createTime 2021年04月05日 14:15:00
 */
@Slf4j
@Service
public class ProduceServiceImpl implements ProducerService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendMessage(OrderDto orderDto) {
//        public void convertAndSend(String channel, Object message)
        //channel 为键   message为值
//        private String pkGoodId;
//        private String userId;
//        private String phone;
//        private int number;
        // 键为product_商品id   值为订单对象的json字符串
        System.out.println("键为：++++++++++++++++++++++++++++++++++++++");
        System.out.println(Constants.REDIS_PRODUCT_PREFIX + orderDto.getPkGoodId());
        System.out.println("键为：++++++++++++++++++++++++++++++++++++++");
        stringRedisTemplate.convertAndSend(Constants.REDIS_PRODUCT_PREFIX + orderDto.getPkGoodId(), JSON.toJSONString(orderDto));
    }
}
