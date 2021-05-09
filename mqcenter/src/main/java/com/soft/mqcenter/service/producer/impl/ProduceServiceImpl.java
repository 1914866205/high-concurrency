package com.soft.mqcenter.service.producer.impl;

import com.alibaba.fastjson.JSON;
import com.soft.mqcenter.common.Constants;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.producer.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

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
    private int number = 0;


    @Override
    public void messageBatchToQueue(LinkedBlockingQueue<OrderDto> queue) {
//        log.info("消息中心收到用户中心queue:"+queue);
        for (OrderDto orderDto : queue) {
            stringRedisTemplate.convertAndSend(Constants.REDIS_PRODUCT_PREFIX + orderDto.getPkGoodId(), JSON.toJSONString(orderDto));
        }
    }
}
