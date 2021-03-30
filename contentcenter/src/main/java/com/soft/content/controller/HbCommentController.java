package com.soft.content.controller;

import com.soft.content.annotation.ControllerWebLog;
import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.CommentDto;
import com.soft.content.service.HbCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/comment/")
@Api(value = "HbCommentController", tags = {"评论模块接口"})
public class HbCommentController {

    @Resource
    private HbCommentService hbCommentService;

    @ApiOperation(value = "添加评论", notes = "添加评论")
    @PostMapping("addComment")
    @ControllerWebLog(name = "addComment", isSaved = true)
    public ResponseResult addComment(@RequestBody CommentDto commentDto) {
        return hbCommentService.addComment(commentDto);
    }

    @ApiOperation(value = "删除评论", notes = "删除评论")
    @PostMapping("delComment")
    @ControllerWebLog(name = "delComment", isSaved = true)
    public ResponseResult delComment(String commentId) {
        return hbCommentService.delComment(commentId);
    }

    @ApiOperation(value = "查询指定商品的所有评论", notes = "查询指定商品的所有评论")
    @PostMapping("selectCommentsById")
    @ControllerWebLog(name = "selectCommentsById", isSaved = true)
    public ResponseResult selectCommentsById(String goodId) {
        return hbCommentService.selectCommentsById(goodId);
    }
}
