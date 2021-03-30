package com.soft.content.service.impl;

import com.soft.content.common.ResponseResult;
import com.soft.content.common.ResultCode;
import com.soft.content.feignclient.UserCenterFeignClient;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.model.entity.HbOrder;
import com.soft.content.model.entity.HbUser;
import com.soft.content.repository.HbGoodRepository;
import com.soft.content.repository.HbOrderRepository;
import com.soft.content.service.HbOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:50:00
 */
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
        HbOrder hbOrder = HbOrder.builder()
                .pkOrderId(UUID.randomUUID().toString().substring(0, 19))
                .state(0)
                .phone(hbOrderDto.getPhone())
                .pkGoodId(hbOrderDto.getPkGoodId())
                .userId(hbOrderDto.getUserId())
                .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        /**
         * 从redis中取该商品的订单排名
         * 1.从redis中取数据
         * 2.该键是否存在
         * 3.如果键存在，取键值对，值+1
         * 4.如果键不存在，添加键值对
         */
        StringBuffer key = new StringBuffer(Objects.requireNonNull(redisTemplate.opsForValue().get("ORDER_" + hbOrderDto.getPkGoodId())));
        if (!StringUtils.isEmpty(key)) {
            StringBuilder value = new StringBuilder(redisTemplate.opsForValue().get(key));
            int i = Integer.parseInt(String.valueOf(value));
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
            ResponseResult result = userCenterFeignClient.findInfoById(hbOrder.getUserId());
            HbUser hbUser = (HbUser) result.getData();

            //查询该商品的价格和剩余数量
            HbGood hbGood = hbGoodRepository.getOne(hbOrder.getPkGoodId());

            System.out.println("*********变化前*************");
            System.out.println(hbUser + "----");
            System.out.println(hbOrder + "----");
            System.out.println(hbGood + "----");
            System.out.println("*******************************");
            //如果购买数量大于库存，则购买失败
            if (hbOrder.getNumber() > hbGood.getCount()) {
                //返回库存不足
                return ResponseResult.failure(ResultCode.Order_OVER);
            } else if (hbUser.getMoney() < hbGood.getPrice() * hbOrder.getNumber()) {
                //返回余额不足
                return ResponseResult.failure(ResultCode.Order_CREDIT_LOW);
            } else {
                //用户余额变化
                hbUser.setMoney(hbUser.getMoney() - hbGood.getPrice() * hbOrder.getNumber());
                userCenterFeignClient.payOrder(hbUser);
                //商品数量减少
                int resultCount = hbGood.getCount() - hbOrder.getNumber();
                hbGood.setCount(resultCount);
                hbGoodRepository.save(hbGood);
                //订单状态改变
                hbOrder.setState(2);
                hbOrderRepository.save(hbOrder);
                System.out.println("*******变化后***************");
                System.out.println(hbUser + "----");
                System.out.println(hbOrder + "----");
                System.out.println(hbGood + "----");
                System.out.println("*******************************");
                return ResponseResult.success();
            }
        }
    }
}
