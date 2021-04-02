package com.soft.seckillcenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderDto.java
 * @Description TODO
 * @createTime 2021年03月30日 13:27:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    /**
     * 商品Id
     */
//    @Column(nullable = false)
    private String pkGoodId;


    /**
     * 用户Id
     */
    private String userId;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 购买数量
     */
    private int number;
}