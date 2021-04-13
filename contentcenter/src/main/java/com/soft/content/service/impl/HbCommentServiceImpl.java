package com.soft.content.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.soft.content.common.ResponseResult;
import com.soft.content.feignclient.UserCenterFeignClient;
import com.soft.content.model.dto.CommentDto;
import com.soft.content.model.entity.HbComment;
import com.soft.content.model.entity.HbUser;
import com.soft.content.model.vo.HbCommentView;
import com.soft.content.repository.HbCommentRepository;
import com.soft.content.service.HbCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbCommentServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:46:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class HbCommentServiceImpl implements HbCommentService {
    private final HbCommentRepository hbCommentRepository;
    private final UserCenterFeignClient userCenterFeignClient;


    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        hbCommentRepository.save(HbComment.builder()
                .pkCommentId(UUID.randomUUID().toString().substring(0, 19))
                .content(commentDto.getContent())
                .pkGoodId(commentDto.getPkGoodId())
                .pkUserIngId(commentDto.getPkUserIngId())
                .pkUserEdId(commentDto.getPkUserEdId())
                .type(commentDto.getType())
                .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delComment(String commentId) {
        hbCommentRepository.deleteById(commentId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectCommentsById(String goodId) {
        List list = new ArrayList();
        HbUser hbUser = null;
        List<HbComment> comments = hbCommentRepository.findHbCommentsByPkGoodId(goodId);
        for(HbComment comment : comments) {
            hbUser = JSONObject.parseObject(userCenterFeignClient.findInfoById(comment.getPkUserIngId()).getData().toString(), HbUser.class);
            System.out.println(hbUser);
            HbCommentView.builder()
                    .pkCommentId(comment.getPkCommentId())
                    .createdTime(comment.getCreatedTime())
                    .pkGoodId(comment.getPkGoodId())
                    .pkUserEdId(comment.getPkUserEdId())
                    .pkUserIngId(comment.getPkUserIngId())
                    .type(comment.getType())
                    .content(comment.getContent())
                    .username(hbUser.getUsername())
                    .avatar(hbUser.getAvatar())
                    .build();
            list.add(HbCommentView.builder()
                    .pkCommentId(comment.getPkCommentId())
                    .createdTime(comment.getCreatedTime())
                    .pkGoodId(comment.getPkGoodId())
                    .pkUserEdId(comment.getPkUserEdId())
                    .pkUserIngId(comment.getPkUserIngId())
                    .type(comment.getType())
                    .content(comment.getContent())
                    .username(hbUser.getUsername())
                    .avatar(hbUser.getAvatar())
                    .build());
        }
        hbUser = null;
        return ResponseResult.success(list);
    }
}
