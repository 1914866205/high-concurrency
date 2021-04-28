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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, Boolean> goodsFlag = new HashMap<>();


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
        //商品的秒杀标志
        List<String> allGoodsId = hbGoodService.findAllGoodsId();
        for (String goodId : allGoodsId) {
            goodsFlag.put(goodId, true);
        }
    }

    @ApiOperation(value = "修改商品状态", notes = "修改商品状态")
    @PostMapping("changeFlag")
    public void changeFlag(@RequestParam String goodId) {
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        log.info("编号" + goodId + "商品已经卖完");
        this.goodsFlag.put(goodId, !this.goodsFlag.get(goodId));
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
        if (goodsFlag.get(orderDto.getPkGoodId())) {
            return hbOrderService.seckOrder(orderDto);
        } else {
            return ResponseResult.failure(ResultCode.GOOD_CLEAN);
        }
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
//        log.info(++num + "进入内容中心添加订单接口：");
        Thread thread = new Thread(new FollowThread(orderDto));
        thread.start();
        return ResponseResult.success();
    }

    /**
     * 批量创建订单
     *
     * @param queue
     * @return
     */
    @ApiOperation(value = "批量添加订单", notes = "批量添加订单")
    @PostMapping("batchAddOrder")
    @ControllerWebLog(name = "batchAddOrder", isSaved = true)
    public ResponseResult batchAddOrder(@RequestBody LinkedBlockingQueue<OrderDto> queue) {
        log.info(++num + "进入内容中心添加订单接口：" + "queue大小:" + queue.size());
        Thread thread = new Thread(new BatchFollowThread(queue));
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
        return hbOrderService.payOrder(orderId);
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

    class BatchFollowThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public BatchFollowThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            hbOrderService.batchAddOrder(queue);
        }
    }

}
