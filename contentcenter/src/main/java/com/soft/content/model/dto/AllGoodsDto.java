package com.soft.content.model.dto;

import com.soft.content.model.entity.HbGood;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author crq
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllGoodsDto {
    private List<HbGood> goods;
    private List<SecKillAddDto> secKillAddDto;
    private String goodId;
    private String type;
    private String description;
    private String goodName;
    private String image;
    private double price;
    private int count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date day;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date detail;
    private String secDescription;
    private List<RuleDto> ruleDtoList;
}
