package com.soft.content.model.dto;

import com.soft.content.model.entity.HbGood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
