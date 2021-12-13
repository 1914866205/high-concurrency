package com.soft.content.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGood.java
 * @Description TODO
 * @createTime 2021年03月26日 09:48:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class HbGood {
    @Id
    private String pkGoodId;

    /**
     * 商品名称
     */
    @Column(nullable = false)
    private String goodName;


    /**
     * 发布人id
     */
    @Column(nullable = false)
    private String userId;


    /**
     * 商品描述
     */
    @Column(nullable = false)
    private String description;

    /**
     * 商品价格
     */
    @Column(nullable = false)
    private double price;

    /**
     * 商品图片
     */
    @Column(nullable = false)
    private String image;

    /**
     * 激活状态
     * 0 未激活
     * 1 已激活
     */
    @Column(nullable = false, length = 2)
    private int status;

    /**
     * 商品类型，即商品标签
     */
    @Column(nullable = false)
    private String type;


    /**
     * 商品数量
     */
    @Column(nullable = false)
    private int count;


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
