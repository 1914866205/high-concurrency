package com.soft.usercenter.controller;

import com.soft.usercenter.annotation.ControllerWebLog;
import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.service.HbOrderService;
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
@RequestMapping()
@Api(value = "HbOrderController", tags = {"订单模块接口"})
public class HbOrderController {

    @Resource
    private HbOrderService hbOrderService;

}
