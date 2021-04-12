package com.soft.content.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.soft.content.common.ResponseResult;
import com.soft.content.common.ResultCode;
import com.soft.content.feignclient.SecKillCenterFeignClient;
import com.soft.content.feignclient.UserCenterFeignClient;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.model.dto.SecResultDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.model.entity.HbOrder;
import com.soft.content.model.entity.HbStrategy;
import com.soft.content.model.entity.HbUser;
import com.soft.content.model.vo.HbOrderView;
import com.soft.content.model.vo.SecResultVo;
import com.soft.content.repository.HbGoodRepository;
import com.soft.content.repository.HbOrderRepository;
import com.soft.content.repository.HbStrategyRepository;
import com.soft.content.repository.HbUserRepository;
import com.soft.content.service.HbOrderService;
import javassist.runtime.Desc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
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
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class HbOrderServiceImpl implements HbOrderService {
    private final HbOrderRepository hbOrderRepository;
    private final HbGoodRepository hbGoodRepository;
    private final HbUserRepository hbUserRepository;
    private final HbStrategyRepository hbStrategyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserCenterFeignClient userCenterFeignClient;
    private final SecKillCenterFeignClient secKillCenterFeignClient;
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
                if (queue.size() == 0) {
                    return;
                }

                //如果10毫秒内只是一个请求，直接单步执行
                if (queue.size() == 1) {
                    number += queue.size();
                    log.info("定时任务被执行" + queue.size() + "number:" + number);
                    System.out.println(queue + "----------");
                    secKillCenterFeignClient.secKill(orderDto);
                    queue.clear();
                    return;
                }

                number += queue.size();
                log.info("定时任务被执行" + queue.size() + "number:" + number);
                System.out.println(queue + "----------");
                Thread thread = new Thread(new SendThread(queue));
                thread.start();
                queue.clear();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public ResponseResult seckOrder(OrderDto orderDto) {
        queue.add(orderDto);
        this.orderDto = orderDto;
        return ResponseResult.success(ResultCode.Order_Send);
    }

    @Override
    public ResponseResult findSecKillUserOrder(SecResultDto secResultDto) {
        List<SecResultVo> result = new ArrayList<>();
        //此处先写死策略时
//        List<HbStrategy> strategies = secResultDto.getStrategies();
        List<HbStrategy> strategies = hbStrategyRepository.findHbStrategiesByGoodIdEquals(secResultDto.getGoodId());
//        如果该策略的商品id不是指定商品的策略id，则移除该策略
//        strategies.removeIf(strategy -> !strategy.getGoodId().equals(secResultDto.getGoodId()));
        //对策略的结束排名进行排序
        strategies.sort(Comparator.comparing(HbStrategy::getRankEnd).reversed());
        //从大到小排
        List<HbOrder> secKillUserOrder = hbOrderRepository.findSecKillUserOrder(secResultDto.getGoodId(), secResultDto.getTime());
        //遍历所有秒杀订单
        for (int i = 0; i < secKillUserOrder.size(); i++) {
            SecResultVo secResultVo = new SecResultVo();
            HbOrder hbOrder = secKillUserOrder.get(i);
            hbOrder.setRank(i + 1);
            HbUser hbUser = hbUserRepository.findById(hbOrder.getUserId()).get();
            //遍历所有该商品的策略，根据时间排位的排名，分别给予不同的折扣
            boolean flag = true;
            for (int j = 0; j < strategies.size(); j++) {
                //如果订单的排名比最大的策略排名还大，就原价
                if (hbOrder.getRank() > strategies.get(j).getRankEnd()) {
                    if (!flag) {
                        break;
                    } else if (secResultVo.getDiscount() == null) {
                        secResultVo.setDiscount(1.00);
                    } else {
                        flag = false;
                    }
                } else {
                    //如果订单排名比最大的排名大，就设置为当前最大的折扣
                    secResultVo.setDiscount(strategies.get(j).getDiscount());
                }
            }
            secResultVo.setUserName(hbUser.getNickname());
            secResultVo.setPhoneNumber(hbOrder.getPhone());
            //保存最终订单排名
            hbOrderRepository.save(hbOrder);
            result.add(secResultVo);
        }

        result.sort(Comparator.comparing(SecResultVo::getDiscount));
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setRank(i + 1);
        }
        System.out.println(result);
        return ResponseResult.success(result);
    }

    /**
     * 添加订单
     *
     * @param hbOrderDto
     * @return
     */
    @Override
    public ResponseResult addOrder(OrderDto hbOrderDto) {
        log.info("进入添加订单服务层");
        HbOrder hbOrder = HbOrder.builder()
                .pkOrderId(UUID.randomUUID().toString().substring(0, 19))
                .state(0)
                .phone(hbOrderDto.getPhone())
                .pkGoodId(hbOrderDto.getPkGoodId())
                .userId(hbOrderDto.getUserId())
                .number(hbOrderDto.getNumber())
                .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        log.info("订单创建" + hbOrder);
        /**
         * 从redis中取该商品的订单排名
         * 1.从redis中取数据
         * 2.该键是否存在
         * 3.如果键存在，取键值对，值+1
         * 4.如果键不存在，添加键值对
         */
        String value = redisTemplate.opsForValue().get("ORDER_" + hbOrderDto.getPkGoodId());
        log.info("value" + value);
        if (!StringUtils.isEmpty(value)) {
            int i = Integer.parseInt(value);
            i++;
            hbOrder.setRank(i);
            redisTemplate.opsForValue().set("ORDER_" + hbOrderDto.getPkGoodId(), String.valueOf(i));
        } else {
            hbOrder.setRank(1);
            redisTemplate.opsForValue().set("ORDER_" + hbOrderDto.getPkGoodId(), "1");
        }
        hbOrderRepository.save(hbOrder);
        return ResponseResult.success();
    }

    /**
     * 取消订单
     *
     * @param hBorderId
     * @return
     */
    @Override
    public ResponseResult celOrder(String hBorderId) {
        Optional<HbOrder> hbOrder = hbOrderRepository.findById(hBorderId);
        hbOrder.get().setState(3);
        hbOrderRepository.save(hbOrder.get());
        return ResponseResult.success();
    }

    /**
     * 支付订单
     *
     * @param hBorderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseResult payOrder(String hBorderId) {
        Optional<HbOrder> hbOrderList = hbOrderRepository.findById(hBorderId);
        HbOrder hbOrder = hbOrderList.get();
        final int state = hbOrder.getState();
        if (state == 3) {
            //如果订单已经取消，则不可支付
            return ResponseResult.failure(ResultCode.ORDER_CANCLE);
        } else if (state == 2) {
            //如果订单已支付，不可重复支付
            return ResponseResult.failure(ResultCode.ORDER_PAY);
        } else {
            //查询用户余额
            ResponseResult consumerResult = userCenterFeignClient.findInfoById(hbOrder.getUserId());

            HbUser hbUserConsumer = JSONObject.parseObject(consumerResult.getData().toString(), HbUser.class);
            //查询该商品的价格和剩余数量
            HbGood hbGood = hbGoodRepository.getOne(hbOrder.getPkGoodId());

            //获取商品的发布者
            String hbUserProduceId = hbGood.getUserId();
            ResponseResult produceResult = userCenterFeignClient.findInfoById(hbUserProduceId);
            HbUser hbUserProduce = JSONObject.parseObject(produceResult.getData().toString(), HbUser.class);

            //对原价进行优惠折算
            Double payMoney = hbGood.getPrice() * hbOrder.getNumber();
            /**
             * 获取当前订单的折扣
             * 获取最终价格
             */


            //对当前商品查找最大的优惠范围
            List<HbStrategy> strategies = hbStrategyRepository.findHbStrategiesByGoodIdEquals(hbGood.getPkGoodId());
            //对策略的结束排名进行排序
            strategies.sort(Comparator.comparing(HbStrategy::getRankEnd).reversed());
            //从大到小排
            System.out.println(strategies);
            List<HbOrder> secKillUserOrder = hbOrderRepository.findSecKillUserOrder(hbOrder.getPkGoodId(), strategies.get(0).getCreatedTime().toString());
            if (!secKillUserOrder.isEmpty()) {
                System.out.println("订单有折扣");
                int maxRank = strategies.get(0).getRankEnd();
                System.out.println("maxRank" + maxRank);
                System.out.println("hbOrder.getRank()" + hbOrder.getRank());
                //如果当前订单的排名在策略范围内
                if (hbOrder.getRank() < maxRank) {
                    System.out.println("进循环");
                    //得到当前订单的排名
                    for (int i = 0; i <strategies.size(); i++) {
                        System.out.println("订单排名" + hbOrder.getRank());
                        System.out.println("当前策略起始"+strategies.get(i).getRankStart());
                        System.out.println("当前策略终点" + strategies.get(i).getRankEnd());
                        System.out.println(strategies.get(i).getRankEnd() >= hbOrder.getRank() && hbOrder.getRank() > strategies.get(i).getRankStart());
                        if (strategies.get(i).getRankEnd() >= hbOrder.getRank() && hbOrder.getRank() > strategies.get(i).getRankStart()) {
                            payMoney = payMoney * strategies.get(i).getDiscount();
                            break;
                        }
                    }
                }
            }


            System.out.println("实际支付" + payMoney);
            //如果购买数量大于库存，则购买失败
            if (hbOrder.getNumber() > hbGood.getCount()) {
                //返回库存不足
                return ResponseResult.failure(ResultCode.Order_OVER);
            } else if (hbUserConsumer.getMoney() < payMoney) {
                //返回余额不足
                return ResponseResult.failure(ResultCode.Order_CREDIT_LOW);
            } else {
                //用户余额变化
                //消费者余额减少
                hbUserConsumer.setMoney(hbUserConsumer.getMoney() - payMoney);
                userCenterFeignClient.payOrder(hbUserConsumer);

                hbUserProduce.setMoney(hbUserProduce.getMoney() + payMoney);
                userCenterFeignClient.payOrder(hbUserProduce);

                //商品数量减少
                int resultCount = hbGood.getCount() - hbOrder.getNumber();
                hbGood.setCount(resultCount);
                hbGoodRepository.save(hbGood);
                //订单状态改变
                hbOrder.setState(2);
                hbOrderRepository.save(hbOrder);
                return ResponseResult.success();
            }
        }
    }

    @Override
    public ResponseResult findUserAllOrder(String pkUserId) {
        List list = new ArrayList();
        HbGood hbGood = null;
        HbUser hbUser = null;
        List<HbOrder> orderList = hbOrderRepository.findHbOrdersByUserIdEquals(pkUserId);
        for (HbOrder order : orderList) {
            hbGood = hbGoodRepository.findHbGoodByPkGoodIdEquals(order.getPkGoodId());
            hbUser = JSONObject.parseObject(userCenterFeignClient.findInfoById(order.getUserId()).getData().toString(), HbUser.class);
            HbOrderView.builder()
                    .pkOrderId(order.getPkOrderId())
                    .createTime(order.getCreatedTime())
                    .status(order.getState())
                    .count(order.getNumber())
                    .description(hbGood.getDescription())
                    .goodName(hbGood.getGoodName())
                    .goodImage(hbGood.getImage())
                    .userName(hbUser.getNickname())
                    .money(order.getNumber() * hbGood.getPrice())
                    .build();

            list.add(HbOrderView.builder()
                    .pkOrderId(order.getPkOrderId())
                    .createTime(order.getCreatedTime())
                    .status(order.getState())
                    .count(order.getNumber())
                    .description(hbGood.getDescription())
                    .goodName(hbGood.getGoodName())
                    .goodImage(hbGood.getImage())
                    .userName(hbUser.getNickname())
                    .money(order.getNumber() * hbGood.getPrice())
                    .build());
        }
        hbUser = null;
        hbGood = null;
        return ResponseResult.success(list);
    }

    class SendThread extends Thread {
        private LinkedBlockingQueue<OrderDto> queue;

        public SendThread(LinkedBlockingQueue<OrderDto> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            secKillCenterFeignClient.barchSeckill(queue);
        }
    }

}

/**
 * {
 * "code": 1,
 * "msg": "成功",
 * "data": [
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 1,
 * "discount": 0.1
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 2,
 * "discount": 0.1
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 3,
 * "discount": 0.5
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 4,
 * "discount": 0.5
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 5,
 * "discount": 0.8
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 6,
 * "discount": 0.8
 * },
 * {
 * "userName": "醉忆丶无回路",
 * "phoneNumber": "18851855106",
 * "rank": 7,
 * "discount": 1.0
 * }
 * ]
 * }
 */
