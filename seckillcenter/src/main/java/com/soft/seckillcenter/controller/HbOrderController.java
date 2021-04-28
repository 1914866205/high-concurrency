package com.soft.seckillcenter.controller;

import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class  HbOrderController {
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
    @PostMapping("batchSecKill")
    @ApiOperation(value = "批量秒杀", notes = "批量秒杀")
    void batchSecKill(@RequestBody LinkedBlockingQueue<OrderDto> queue) {
        number += queue.size();
        log.info("秒杀中心批量接收到的队列大小："+ queue.size());
        Thread thread = new Thread(new FollowThread(queue));
        thread.start();
    }


    @PostMapping("secKill")
    @ApiOperation(value = "单个秒杀", notes = "单个秒杀")
    ResponseResult secKill(@RequestBody OrderDto orderDto) {
//        log.info("秒杀中心收到单个请求:" + orderDto);
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
                    redissonLock.lock();
                    hbOrderService.toSecKill(orderDto);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    redissonLock.unlock();
                }
            }

        }
    }
}
