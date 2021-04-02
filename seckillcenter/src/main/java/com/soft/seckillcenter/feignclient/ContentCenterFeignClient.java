package com.soft.seckillcenter.feignclient;

import com.soft.seckillcenter.common.ResponseResult;
import com.soft.seckillcenter.model.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年03月30日 14:52:00
 */
@FeignClient(name = "content-center")
public interface ContentCenterFeignClient {


    /**
     * 返回调用内容中心添加订单接口
     *
     * @param orderDto
     * @return
     */
    @PostMapping("order/addOrder")
    ResponseResult addOrder(@RequestBody OrderDto orderDto);
}

