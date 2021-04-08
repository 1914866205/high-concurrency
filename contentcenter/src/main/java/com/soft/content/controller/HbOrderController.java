package com.soft.content.controller;

import com.soft.content.annotation.ControllerWebLog;
import com.soft.content.common.Constants;
import com.soft.content.common.ResponseResult;
import com.soft.content.common.ResultCode;
import com.soft.content.feignclient.SecKillCenterFeignClient;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.model.dto.SecResultDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.model.entity.HbStrategy;
import com.soft.content.service.HbGoodService;
import com.soft.content.service.HbOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
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
@RequestMapping(value = "/order/")
@Api(value = "HbOrderController", tags = {"订单模块接口"})
public class HbOrderController {

    @Resource
    private HbOrderService hbOrderService;
    @Resource
    private HbGoodService hbGoodService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private int num = 0;

    /**
     * 在Servlet初始化之前加载一些缓存数据等
     * 先把各大产品的剩余量放到redis中
     */
    @PostConstruct
    public void init() {
        List<HbGood> hbGoodList = hbGoodService.getAllGoods();
        for (HbGood hbGood : hbGoodList) {
            redisTemplate.opsForValue().set(Constants.REDIS_PRODUCT_STOCK_PREFIX + hbGood.getPkGoodId(), hbGood.getCount() + "");
        }
    }


    /**
     * 压测流程
     * 1.初始化redis中的商品个数
     * 2.Jmeter发送一万个请求
     * 3.redis存储商品剩余量
     * 4.消息队列存储 用户id，商品id
     * <p>
     * <p>
     * *****************  此orderDto的number限制为1
     *
     * @return
     */
    @ApiOperation(value = "秒杀订单", notes = "秒杀订单")
    @PostMapping("spikeOrder")
    public ResponseResult spikeOrder(@RequestBody OrderDto orderDto) {
        //  负载均衡，高并发，请求秒杀服务
//        log.info("内容中心收到秒杀订单:" + orderDto.toString());
        return hbOrderService.seckOrder(orderDto);
//        return secKillCenterFeignClient.secKill(orderDto);
    }


    /**
     * 创建订单
     *
     * @param orderDto
     * @return
     */
    @ApiOperation(value = "添加订单", notes = "添加订单")
    @PostMapping("addOrder")
    @ControllerWebLog(name = "addOrder", isSaved = true)
    public ResponseResult addOrder(@RequestBody OrderDto orderDto) {
        log.info(++num + "进入内容中心添加订单接口：");
        Thread thread = new Thread(new FollowThread(orderDto));
        thread.start();
        return ResponseResult.success();
    }

    /**
     * 根据订单Id取消订单
     *
     * @param hBorderId
     * @return
     */
    @ApiOperation(value = "取消订单", notes = "celOrder")
    @PostMapping("celOrder")
    @ControllerWebLog(name = "celOrder", isSaved = true)
    public ResponseResult celOrder(String hBorderId) {
        return ResponseResult.success(hbOrderService.celOrder(hBorderId));
    }

    /**
     * 支付订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "支付订单", notes = "payOrder")
    @PostMapping("payOrder")
    @ControllerWebLog(name = "payOrder", isSaved = true)
    public ResponseResult payOrder(String orderId) {
        return ResponseResult.success(hbOrderService.payOrder(orderId));
    }


    /**
     * 查看该用户所有订单
     *
     * @param pkUserId
     * @return
     */
    @ApiOperation(value = "查看该用户所有订单", notes = "findUserAllOrder")
    @PostMapping("findUserAllOrder")
    @ControllerWebLog(name = "findUserAllOrder", isSaved = true)
    public ResponseResult findUserAllOrder(String pkUserId) {
        return ResponseResult.success(hbOrderService.findUserAllOrder(pkUserId));
    }

    /**
     * 按照抢购顺序公示前1000名客户名称、手机号码（星号隐藏第4-7位）及对应折扣。
     *
     * @return
     */
    @ApiOperation(value = "顺序公示前1000名", notes = "findSecKillUserOrder")
    @PostMapping("findSecKillUserOrder")
    @ControllerWebLog(name = "findSecKillUserOrder", isSaved = true)
    public ResponseResult findSecKillUserOrder(@RequestBody SecResultDto secResultDto) {
         return hbOrderService.findSecKillUserOrder(secResultDto);
    }




    class FollowThread extends Thread {
        private OrderDto orderDto;

        public FollowThread(OrderDto orderDto) {
            this.orderDto = orderDto;
        }

        @Override
        public void run() {
            hbOrderService.addOrder(orderDto);
        }
    }

}


//    /**
//     * 压测流程
//     * 1.初始化redis中的商品个数
//     * 2.Jmeter发送一万个请求
//     * 3.redis存储商品剩余量
//     * 4.消息队列存储 用户id，商品id
//     * <p>
//     * <p>
//     * *****************  此orderDto的number限制为1
//     *
//     * @return
//     */
//    @ApiOperation(value = "秒杀订单", notes = "秒杀订单")
//    @PostMapping("spikeOrder")
//    public ResponseResult spikeOrder(@RequestBody OrderDto orderDto) {
//        //  负载均衡，高并发，请求秒杀服务
//        log.info("内容中心收到秒杀订单:" + orderDto.toString());
//        return secKillCenterFeignClient.secKill(orderDto);
//
////        //1.redis先库存-1，要使用decrement方法，底层是线程安全的
////        //返回的数据是减完之后的数字
////        Long stock = redisTemplate.opsForValue().decrement(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
////        if (stock < 0) {
////            //如果库存＜0   防止少卖
////            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
////            return ResponseResult.failure(ResultCode.GOOD_CLEAN);
////        }
////
////        //2.如果有剩余商品
////        //发送消息给消息队列，传输用户id和订单信息，异步传输数据
////        //秒杀活动每人限抢一个
////        try {
////            hbOrderService.secKill(orderDto);
////        } catch (Exception e) {
////            //如果遇到异常，回滚事务
////            redisTemplate.opsForValue().increment(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId());
////            log.info("订单创建失败");
////            return ResponseResult.failure(ResultCode.ORDER_CREATE_ERROR);
////        }
////
////        log.info("订单创建成功" + redisTemplate.opsForValue().get(Constants.REDIS_PRODUCT_STOCK_PREFIX + orderDto.getPkGoodId()));
////        return ResponseResult.success();
////
////        //3.消息队列监听到数据变化，创建订单，此处先用RabbitMQ替代
//
//    }