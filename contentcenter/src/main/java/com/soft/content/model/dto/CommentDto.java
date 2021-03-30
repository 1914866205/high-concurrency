package com.soft.content.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CommentDto.java
 * @Description TODO
 * @createTime 2021年03月30日 12:25:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    /**
     * 商品Id
     */
    private String pkGoodId;
    /**
     * 评论者Id
     */
    private String pkUserIngId;
    /**
     * 被评论者Id
     */
    private String pkUserEdId;
    /**
     * 星级评论 1-5星
     */
    private int type;
    /**
     * 评论内容
     */
    private String content;
}
