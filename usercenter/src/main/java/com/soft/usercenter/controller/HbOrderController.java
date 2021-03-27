package com.soft.usercenter.controller;

import com.soft.usercenter.service.HbCommentService;
import com.soft.usercenter.service.HbOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping
@Api(value = "HbOrderController", tags = {"订单模块接口"})
public class HbOrderController {

    @Resource
    private HbOrderService hbOrderService;


}
