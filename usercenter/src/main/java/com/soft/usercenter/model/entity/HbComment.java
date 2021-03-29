package com.soft.usercenter.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbComment.java
 * @Description TODO
 * @createTime 2021年03月26日 09:48:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class HbComment {
    /**
     * 主键ID
     */
    @Id
    private String pkCommentId;

    /**
     * 商品Id
     */
    @Column(nullable = false, length = 20)
    private String pkGoodId;


    /**
     * 评论者Id
     */
    @Column(nullable = false, length = 20)
    private String pkUserIngId;

    /**
     * 被评论者Id
     */
    @Column(nullable = false, length = 20)
    private String pkUserEdId;


    /**
     * 评论者Id
     */
    @Column(nullable = false,length = 20)
    private int type;

    /**
     * 评论内容
     */
    @Column(nullable = true)
    private String content;


    /**
     * 创建时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createdTime;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updatedTime;

}

