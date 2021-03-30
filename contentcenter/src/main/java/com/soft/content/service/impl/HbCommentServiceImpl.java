package com.soft.content.service.impl;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.CommentDto;
import com.soft.content.model.entity.HbComment;
import com.soft.content.repository.HbCommentRepository;
import com.soft.content.service.HbCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class HbCommentServiceImpl implements HbCommentService {
    @Resource
    private HbCommentRepository hbCommentRepository;


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
        return ResponseResult.success(hbCommentRepository.findHbCommentsByPkGoodId(goodId));
    }
}
