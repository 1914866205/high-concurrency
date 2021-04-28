package com.soft.mqcenter.service.consumer.impl;

import com.alibaba.fastjson.JSON;
import com.soft.mqcenter.common.Constants;
import com.soft.mqcenter.config.RedisUtil;
import com.soft.mqcenter.feignclient.ContentCenterFeignClient;
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
    private ContentCenterFeignClient contentCenterFeignClient;
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

                    //如果1000毫秒内只是一个请求，直接单步执行
                    if (queue.size() == 1) {
                        number += queue.size();
//                        log.info("定时任务被执行" + queue.size() + "number:" + number);
//                        System.out.println(queue + "----------");
                        contentCenterFeignClient.addOrder(orderDto);
                        queue.clear();
                        return;
                    }

                    number += queue.size();
//                    log.info("定时任务被执行" + queue.size() + "number:" + number);
//                    System.out.println(queue + "----------");
                    //之所以要新建队列对象，因为并发操作，可能造成先清队列再创建线程的数据丢失
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
//        log.info(++num + "队列监听到订单信息：");
//        log.info("队列监听到订单信息：" + message);

        //json对象反序列化
        orderDto = JSON.parseObject(message, OrderDto.class);
        queue.add(orderDto);
        //由内容中心创建订单
        //此处可以用自旋转 调用线程池的线程重复创建
//        log.info("队列发送订单信息给内容中心，由内容中心创建订单:" + orderDto);
        //监听后批量发送
    }

    class SendThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public SendThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            log.info("消息队列发送的queue"+queue.size());
            contentCenterFeignClient.batchAddOrder(queue);
        }
    }

}
