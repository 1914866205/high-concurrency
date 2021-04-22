package com.soft.content.controller;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.SecKillAddDto;
import com.soft.content.service.HbStrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;


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
@RequestMapping(value = "/hbStrategy/")
@Api(value = "HbStrategyController", tags = {"策略模块接口"})
public class HbStrategyController {

    @Resource
    private HbStrategyService hbStrategyService;

    @PostMapping("add")
    @ApiOperation(value = "添加策略", notes = "添加策略")
    ResponseResult addHbStrategy(@RequestBody SecKillAddDto secKillAddDto) throws ParseException {
        return hbStrategyService.addStrategy(secKillAddDto);
    }

    @PostMapping("get")
    @ApiOperation(value = "根据商品ID获取策略", notes = "根据ID获取策略")
    ResponseResult getHbStrategy(String goodId)  {
        return hbStrategyService.getStrategy(goodId);
    }
}