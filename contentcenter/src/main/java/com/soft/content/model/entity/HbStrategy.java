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
 * @ClassName HbStrategy.java
 * @Description TODO
 * @createTime 2021年03月26日 09:48:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class HbStrategy {
    /**
     * 主键ID
     */
    @Id
    private String pkStrategyId;

    /**
     * 策略名称
     */
    @Column(nullable = false, length = 20)
    private String strategyName;


    /**
     * 商品Id
     */
    @Column(nullable = false, length = 20)
    private String goodId;

    /**
     * 起始地址
     */
    @Column(nullable = false, length = 8)
    private int rankStart;

    /**
     * 起始地址
     */
    @Column(nullable = false, length = 8)
    private int rankEnd;

    /**
     * 折扣
     */
    @Column(nullable = false)
    private Double discount;


    /**
     * 创建时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;
}
