package com.soft.seckillcenter.controller;

import com.soft.seckillcenter.common.Constants;
import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.common.ResultCode;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
import io.swagger.annotations.Api;
import jodd.datetime.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private HbOrderService hbOrderService;

    @Autowired
    private Redisson redisson;

    @PostMapping("secKill")
    ResponseResult secKill(@RequestBody OrderDto orderDto) {
        //加锁
        String lockKey = orderDto.getPkGoodId();
        //设置线程ID,作为锁， 因为如果是固定的lockkey，在高并发场景下，锁会永久失效
        String clientId = UUID.randomUUID().toString();
        //为了防止中间代码异常，锁无法解开，要try catch，同时finally保证锁会被解开
        RLock redissonLock = redisson.getLock(lockKey);

        try {

            //boolean result=redisTemplate.opsForValue().setIfAbsent(lockKye,"lock");
//为了防止系统直接宕机，锁无法解开，要给锁设置过期时间10秒
//restTemplate.expire(lockKey,10,TimeUnit.SECONDS)
//            boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
//            if (!result) {
//                return ResponseResult.failure(ResultCode.INTERFACE_IN_LOCK);
//            }
            //加锁, 底层= setIfAbsent
            redissonLock.lock();
            System.out.println(1);
            log.info(lockKey+"已上锁");
            System.out.println(2);
            hbOrderService.toSecKill(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //开锁
//            if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
//                redisTemplate.delete(lockKey);
//            }
            System.out.println(9);

            redissonLock.unlock();
            log.info(lockKey + "已解锁");
            System.out.println(10);

            return ResponseResult.failure(ResultCode.SERVER_ERROR);
        }

    }
}
