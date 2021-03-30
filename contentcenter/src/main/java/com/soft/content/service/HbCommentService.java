package com.soft.content.service;


import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.CommentDto;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbCommentService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:46:00
 */
public interface HbCommentService {

    /**
     * 添加评论
     * @param commentDto
     * @return
     */
    ResponseResult addComment(CommentDto commentDto);

    /**
     * 删除评论
     * @param goodId
     * @return
     */
    ResponseResult delComment(String goodId);

    /**
     * 跟商品Id查询所有商品
     * @param goodId
     * @return
     */
    ResponseResult selectCommentsById(String goodId);
}
