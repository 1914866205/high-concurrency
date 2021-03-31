package com.soft.content.controller;

import com.soft.content.annotation.ControllerWebLog;
import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.CommentDto;
import com.soft.content.model.dto.OrderDto;
import com.soft.content.service.HbOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/order/")
@Api(value = "HbOrderController", tags = {"订单模块接口"})
public class HbOrderController {

    @Resource
    private HbOrderService hbOrderService;

    /**
     * 创建订单
     *
     * @param OrderDto
     * @return
     */
    @ApiOperation(value = "添加订单", notes = "添加订单")
    @PostMapping("addOrder")
    @ControllerWebLog(name = "addOrder", isSaved = true)
    public ResponseResult addOrder(@RequestBody OrderDto OrderDto) {
        log.info("进入添加订单接口");
        return ResponseResult.success(hbOrderService.addOrder(OrderDto));
    }

    /**
     * 根据订单Id取消订单
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
     * @param orderId
     * @return
     */
    @ApiOperation(value = "支付订单", notes = "payOrder")
    @PostMapping("payOrder")
    @ControllerWebLog(name = "payOrder", isSaved = true)
    public ResponseResult payOrder(String orderId) {
        return ResponseResult.success(hbOrderService.payOrder(orderId));
    }


}
