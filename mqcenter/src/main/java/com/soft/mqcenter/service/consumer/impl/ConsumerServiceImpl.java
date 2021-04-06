package com.soft.mqcenter.service.consumer.impl;

import com.alibaba.fastjson.JSON;
import com.soft.mqcenter.config.RedisUtil;
import com.soft.mqcenter.feignclient.ContentCenterFeignClient;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.consumer.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ConsumerServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月05日 14:15:00
 */
@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private ContentCenterFeignClient contentCenterFeignClient;

    @Override
    public void receiveMessage(String message) {
        log.info("队列监听到订单信息：" + message);

        //json对象反序列化
        OrderDto orderDto = JSON.parseObject(message, OrderDto.class);

        //由内容中心创建订单
        //此处可以用自旋转 调用线程池的线程重复创建
        log.info("队列发送订单信息给内容中心，由内容中心创建订单:" + orderDto);
        contentCenterFeignClient.addOrder(orderDto);
    }
}
