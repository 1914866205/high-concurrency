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
 * @ClassName HbOrder.java
 * @Description TODO
 * @createTime 2021年03月26日 09:47:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class HbOrder {
    @Id
    private String pkOrderId;

    /**
     * 商品Id
     */
    @Column(nullable = false)
    private String pkGoodId;
    /**
     * 订单排名
     */
    @Column(nullable = false, length = 8)
    private int rank;

    /**
     * 用户Id
     */
    @Column(nullable = false, length = 20)
    private String userId;


    /**
     * 手机号
     */
    @Column(nullable = true, length = 21, unique = true)
    private String phone;


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
