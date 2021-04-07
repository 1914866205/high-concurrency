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

import javax.annotation.Resource;

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
    private MQCenterFeignClient mqCenterFeignClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public ResponseResult toSecKill(OrderDto orderDto) {

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

            //先把其他逻辑仅仅作为打印
//            System.out.println("秒杀中心把订单发给消息中心");
            mqCenterFeignClient.messageToQueue(orderDto);
        } catch (Exception e) {
            //如果遇到异常，回滚事务
            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
            log.info("订单创建失败");
            return ResponseResult.failure(ResultCode.ORDER_CREATE_ERROR);
        }
        return ResponseResult.success();
    }
}
