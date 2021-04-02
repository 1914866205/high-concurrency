package com.soft.seckillcenter.controller;

import com.soft.seckillcenter.common.Constants;
import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.common.ResultCode;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("secKill")
    ResponseResult secKill(@RequestBody OrderDto orderDto) {

        //1.redis先库存-1，要使用decrement方法，底层是线程安全的
        //返回的数据是减完之后的数字
        Long stock = redisTemplate.opsForValue().decrement(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
        if (stock < 0) {
            //如果库存＜0   防止少卖
            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
            return ResponseResult.failure(ResultCode.GOOD_CLEAN);
        }

        //2.如果有剩余商品
        //发送消息给消息队列，传输用户id和订单信息，异步传输数据
        //秒杀活动每人限抢一个
        try {
            hbOrderService.secKill(orderDto);
        } catch (Exception e) {
            //如果遇到异常，回滚事务
            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
            log.info("订单创建失败");
            return ResponseResult.failure(ResultCode.ORDER_CREATE_ERROR);
        }

        log.info("订单创建成功" + redisTemplate.opsForValue().get(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId()));
        return ResponseResult.success();

        //3.消息队列监听到数据变化，创建订单，此处先用RabbitMQ替代


    }
}
