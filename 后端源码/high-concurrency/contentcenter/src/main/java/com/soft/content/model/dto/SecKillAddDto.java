package com.soft.content.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SecKillAddDto.java
 * @Description TODO
 * @createTime 2021年04月11日 14:42:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecKillAddDto {
    private String goodId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date day;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date detail;
    private String description;
    private List<RuleDto> ruleDtoList;
}
