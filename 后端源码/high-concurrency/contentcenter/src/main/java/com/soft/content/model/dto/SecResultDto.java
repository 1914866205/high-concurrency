package com.soft.content.model.dto;

import com.soft.content.model.entity.HbStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SecResult.java
 * @Description TODO
 * @createTime 2021年04月08日 13:32:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecResultDto {
    private String time;
    private String goodId;
    private List<HbStrategy> strategies;
}
