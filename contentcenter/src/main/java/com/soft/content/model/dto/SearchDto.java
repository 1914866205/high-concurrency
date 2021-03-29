package com.soft.content.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SearchDto.java
 * @Description TODO
 * @createTime 2021年03月29日 16:52:00
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String content;
    private int currentPage;
    private int pageSize;
}