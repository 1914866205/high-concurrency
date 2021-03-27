package com.soft.content.controller;

import com.soft.content.service.HbCommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbCommentController.java
 * @Description TODO
 * @createTime 2021年03月27日 09:54:00
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping
@Api(value = "HbCommentController", tags = {"评论模块接口"})
public class HbCommentController {

    @Resource
    private HbCommentService hbCommentService;

}
