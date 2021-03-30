package com.soft.content.controller;

import com.soft.content.annotation.ControllerWebLog;
import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.SearchDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.service.HbGoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGoodController.java
 * @Description TODO
 * @createTime 2021年03月27日 09:58:00
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping(value = "/goods/")
@Api(value = "HbGoodController", tags = {"商品模块接口"})
public class HbGoodController {

    @Resource
    private HbGoodService hbGoodService;

    /**
     * 根据商品名称模糊搜索
     *
     * @param searchDto
     * @return
     */
    @ApiOperation(value = "模糊搜索", notes = "根据商品名称模糊查询")
    @PostMapping("search")
    @ControllerWebLog(name = "searchByContent", isSaved = true)
    ResponseResult search(@RequestBody SearchDto searchDto) {
        log.info("-----search-----请求参数：" + searchDto + "**1**");
        Map<String, Object> map = new HashMap<String, Object>();
        //查询符合的商品
        final Page<HbGood> hbGoods = hbGoodService.findGoodsByContent(searchDto);
        map.put("Goods", hbGoods);
        return ResponseResult.success(map);
    }

    /**
     * 查询指定用户的所有商品
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询指定用户的所有商品", notes = "查询指定用户的所有商品")
    @PostMapping("findGoodsByUserId")
    @ControllerWebLog(name = "findGoodsByUserId", isSaved = true)
    ResponseResult findGoodsByUserId(String userId) {
        log.info("-----userId-----请求参数：" + userId + "**1**");
        Map<String, Object> map = new HashMap<String, Object>();
        //查询符合的商品
        map.put("Goods", hbGoodService.findGoodsByUserId(userId));
        return ResponseResult.success(map);
    }

    /**
     * 查询所有商品
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询所有商品", notes = "查询所有商品")
    @PostMapping("findAllGoods")
    @ControllerWebLog(name = "findAllGoods", isSaved = true)
    ResponseResult findAllGoods(SearchDto searchDto) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Goods", hbGoodService.findAllGoods(searchDto));
        return ResponseResult.success(map);
    }


    /**
     * 根据类型给商品分类
     */
    @ApiOperation(value = "根据类型给商品分类", notes = "根据类型给商品分类")
    @PostMapping("findGoodsByType")
    @ControllerWebLog(name = "findGoodsByType", isSaved = true)
    ResponseResult findGoodsByType() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Goods", hbGoodService.findGoodsByType());
        return ResponseResult.success(map);
    }



}
