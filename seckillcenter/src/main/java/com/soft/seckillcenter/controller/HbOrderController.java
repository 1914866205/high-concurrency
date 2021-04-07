package com.soft.seckillcenter.controller;

import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderController.java
 * @Description TODO
 * @createTime 2021年03月27日 09:55:00
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/order/")
@Api(value = "HbOrderController", tags = {"秒杀接口"})
public class HbOrderController {
    private int number = 0;

    @Resource
    private HbOrderService hbOrderService;

    @Autowired
    private Redisson redisson;

    /**
     * 批量分批发送
     *
     * @param queue
     */
    @PostMapping("barchSecKill")
    void barchSeckill(@RequestBody LinkedBlockingQueue<OrderDto> queue) {
        number += queue.size();
        log.info("秒杀中心收到秒杀请求_number:" + number);
        Thread thread = new Thread(new FollowThread(queue));
        thread.start();
    }


    @PostMapping("secKill")
    ResponseResult secKill(@RequestBody OrderDto orderDto) {
        log.info("秒杀中心收到秒杀请求:" + orderDto);
        //加锁
        String lockKey = orderDto.getPkGoodId();
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            //加锁, 底层= setIfAbsent
            redissonLock.lock();
            hbOrderService.toSecKill(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //开锁
            redissonLock.unlock();
            return ResponseResult.success();
        }

    }

    class FollowThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public FollowThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (OrderDto orderDto : queue) {
                //加锁
                String lockKey = orderDto.getPkGoodId();
                RLock redissonLock = redisson.getLock(lockKey);
                try {
                    //加锁, 底层= setIfAbsent
                    log.info(Thread.currentThread()+"加锁");
                    redissonLock.lock();
                    hbOrderService.toSecKill(orderDto);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //开锁
                    log.info(Thread.currentThread()+"开锁");
                    redissonLock.unlock();
                }
            }

        }
    }
}


//    @PostMapping("secKill")
//    ResponseResult secKill(@RequestBody OrderDto orderDto) {
//        log.info("秒杀中心收到秒杀请求:"+orderDto);
//        //加锁
//        String lockKey = orderDto.getPkGoodId();
//        //设置线程ID,作为锁， 因为如果是固定的lockkey，在高并发场景下，锁会永久失效
////        String clientId = UUID.randomUUID().toString();
//        //为了防止中间代码异常，锁无法解开，要try catch，同时finally保证锁会被解开
//        RLock redissonLock = redisson.getLock(lockKey);
//
//        try {
//
//            //boolean result=redisTemplate.opsForValue().setIfAbsent(lockKye,"lock");
////为了防止系统直接宕机，锁无法解开，要给锁设置过期时间10秒
////restTemplate.expire(lockKey,10,TimeUnit.SECONDS)
////            boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
////            if (!result) {
////                return ResponseResult.failure(ResultCode.INTERFACE_IN_LOCK);
////            }
//            //加锁, 底层= setIfAbsent
//            redissonLock.lock();
//            hbOrderService.toSecKill(orderDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //开锁
////            if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
////                redisTemplate.delete(lockKey);
////            }
//
//            redissonLock.unlock();
//
//            return ResponseResult.success();
//        }
//
//    }