package com.soft.seckillcenter.service.impl;

import com.soft.seckillcenter.common.Constants;
import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.common.ResultCode;
import com.soft.seckillcenter.feignclient.ContentCenterFeignClient;
import com.soft.seckillcenter.feignclient.MQCenterFeignClient;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:50:00
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class HbOrderServiceImpl implements HbOrderService {
    @Resource
    private ContentCenterFeignClient contentCenterFeignClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private LinkedBlockingQueue<OrderDto> queue = new LinkedBlockingQueue<>();
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
                        contentCenterFeignClient.addOrder(queue.poll());
                        queue.clear();
                        return;
                    }

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
    public ResponseResult toSecKill(OrderDto orderDto) {
        Long stock = redisTemplate.opsForValue().decrement(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
        if (stock < 0) {
            //如果库存＜0   防止少卖
            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());

            //基本卖完了,通知不要再接收请求了
            contentCenterFeignClient.changeFlag(orderDto.getPkGoodId(),false);
            return ResponseResult.failure(ResultCode.GOOD_CLEAN);
        }
        try {
//            contentCenterFeignClient.addOrder(orderDto);
            this.orderDto = orderDto;
            queue.add(orderDto);
        } catch (Exception e) {
            //如果遇到异常，回滚事务
            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
            log.info("订单创建失败");
            return ResponseResult.failure(ResultCode.ORDER_CREATE_ERROR);
        }
        return ResponseResult.success();
    }


    class SendThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public SendThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            log.info("秒杀中心发送的queue" + queue.size());
            contentCenterFeignClient.batchAddOrder(queue);
        }
    }
}
