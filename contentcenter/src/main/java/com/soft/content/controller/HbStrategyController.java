package com.soft.content.controller;

import com.soft.content.service.HbStrategyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbStrategyController.java
 * @Description TODO
 * @createTime 2021年03月27日 09:59:00
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping
@Api(value = "HbStrategyController", tags = {"策略模块接口"})
public class HbStrategyController {

    @Resource
    private HbStrategyService hbStrategyService;

}
