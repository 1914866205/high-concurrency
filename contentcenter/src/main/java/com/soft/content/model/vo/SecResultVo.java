package com.soft.content.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SecResultVo.java
 * @Description TODO
 * @createTime 2021年04月08日 14:00:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecResultVo {
    private String userName;
    private String phoneNumber;
    private int ranked;
    private Double discount;
}
