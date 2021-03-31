package com.soft.content.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.soft.content.common.ResponseResult;
import com.soft.content.common.ResultCode;
import com.soft.content.feignclient.UserCenterFeignClient;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.model.entity.HbOrder;
import com.soft.content.model.entity.HbUser;
import com.soft.content.model.vo.HbOrderView;
import com.soft.content.repository.HbGoodRepository;
import com.soft.content.repository.HbOrderRepository;
import com.soft.content.repository.HbUserRepository;
import com.soft.content.service.HbOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
    @Resource
    private HbOrderRepository hbOrderRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserCenterFeignClient userCenterFeignClient;
    private final HbGoodRepository hbGoodRepository;

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
//            log.info("***********************");
//            log.info(consumerResult.toString());
//            log.info(consumerResult.getData().toString());
//            log.info("***********************");


            HbUser hbUserConsumer = JSONObject.parseObject(consumerResult.getData().toString(), HbUser.class);
            //查询该商品的价格和剩余数量
            HbGood hbGood = hbGoodRepository.getOne(hbOrder.getPkGoodId());

            //获取商品的发布者
            String hbUserProduceId = hbGood.getUserId();
            ResponseResult produceResult = userCenterFeignClient.findInfoById(hbUserProduceId);
            HbUser hbUserProduce = JSONObject.parseObject(produceResult.getData().toString(), HbUser.class);

//            log.info("*********变化前*************");
//            log.info(hbUserConsumer + "----" + hbUserConsumer);
//            log.info(hbUserProduce + "----" + hbUserProduce);
//            log.info(hbOrder + "----" + hbOrder);
//            log.info(hbGood + "----" + hbGood);
//            log.info("*******************************");
            //如果购买数量大于库存，则购买失败
            if (hbOrder.getNumber() > hbGood.getCount()) {
                //返回库存不足
                return ResponseResult.failure(ResultCode.Order_OVER);
            } else if (hbUserConsumer.getMoney() < hbGood.getPrice() * hbOrder.getNumber()) {
                //返回余额不足
                return ResponseResult.failure(ResultCode.Order_CREDIT_LOW);
            } else {
                //用户余额变化
                //消费者余额减少
                hbUserConsumer.setMoney(hbUserConsumer.getMoney() - hbGood.getPrice() * hbOrder.getNumber());
                userCenterFeignClient.payOrder(hbUserConsumer);

                hbUserProduce.setMoney(hbUserProduce.getMoney() + hbGood.getPrice() * hbOrder.getNumber());
                userCenterFeignClient.payOrder(hbUserProduce);

                //商品数量减少
                int resultCount = hbGood.getCount() - hbOrder.getNumber();
                hbGood.setCount(resultCount);
                hbGoodRepository.save(hbGood);
                //订单状态改变
                hbOrder.setState(2);
                hbOrderRepository.save(hbOrder);
//                System.out.println("*******变化后********");
//                System.out.println(hbUserConsumer + "----" + hbUserConsumer);
//                System.out.println(hbUserProduce + "----" + hbUserProduce);
//                System.out.println(hbOrder + "----" + hbOrder);
//                System.out.println(hbGood + "----" + hbGood);
//                System.out.println("********************");
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
}
