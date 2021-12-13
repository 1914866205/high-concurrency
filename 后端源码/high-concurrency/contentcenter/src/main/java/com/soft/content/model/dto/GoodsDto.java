package com.soft.content.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author crq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDto {
    /**
     * 发布者Id
     */
    private String pkUserIngId;
    private String type;
    private String description;
    private String goodName;
    private String image;
    private double price;
    private int count;
}
