package com.soft.content.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName RuleDto.java
 * @Description TODO
 * @createTime 2021年04月11日 14:45:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleDto {
    private int start;
    private int end;
    private int discount;
}
