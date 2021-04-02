package com.soft.seckillcenter.service.impl;

import com.soft.seckillcenter.feignclient.ContentCenterFeignClient;
import com.soft.seckillcenter.model.dto.OrderDto;
import com.soft.seckillcenter.service.HbOrderService;
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
@Service
@Transactional(rollbackFor = {RuntimeException.class})
public class HbOrderServiceImpl implements HbOrderService {
    @Resource
    private ContentCenterFeignClient contentCenterFeignClient;

    @Override
    public void secKill(OrderDto orderDto) {
        contentCenterFeignClient.addOrder(orderDto);
    }
}
