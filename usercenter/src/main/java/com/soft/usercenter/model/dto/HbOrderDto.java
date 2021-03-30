package com.soft.usercenter.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

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
public class HbOrderDto {
    /**
     * 商品Id
     */
    @Column(nullable = false)
    private String pkGoodId;


    /**
     * 用户Id
     */
    private String userId;


    /**
     * 手机号
     */
    private String phone;

}
