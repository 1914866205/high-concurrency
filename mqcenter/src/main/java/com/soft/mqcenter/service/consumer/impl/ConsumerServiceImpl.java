package com.soft.mqcenter.service.consumer.impl;

import com.alibaba.fastjson.JSON;
import com.soft.mqcenter.common.Constants;
import com.soft.mqcenter.config.RedisUtil;
import com.soft.mqcenter.feignclient.ContentCenterFeignClient;
import com.soft.mqcenter.feignclient.SecKillCenterFeignClient;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.consumer.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private SecKillCenterFeignClient secKillCenterFeignClient;
    @Resource
    private RedisTemplate redisTemplate;
    private int num;
    LinkedBlockingQueue<OrderDto> queue = new LinkedBlockingQueue<>();
    private int number = 0;
    private OrderDto orderDto = new OrderDto();

    @PostConstruct
    public void init() {
        //声明定时任务
        ScheduledExecutorService sh = Executors.newScheduledThreadPool(1);
        sh.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (queue.size() == 0) {
                        return;
                    }

                    if (queue.size() == 1) {
                        number += queue.size();
                        secKillCenterFeignClient.secKill(orderDto);
                        queue.clear();
                        return;
                    }

                    number += queue.size();
                    Thread thread = new Thread(new SendThread(new LinkedBlockingQueue<>(queue)));
                    thread.start();
                    queue.clear();
                } catch (Exception e) {
                    redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId(), queue.size());
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    @Override
    public void receiveMessage(String message) {
        orderDto = JSON.parseObject(message, OrderDto.class);
        queue.add(orderDto);
    }

    class SendThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public SendThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            log.info("消息中心监听到消息，并分批转发"+queue.size());
            secKillCenterFeignClient.batchSeckill(queue);
        }
    }

}
