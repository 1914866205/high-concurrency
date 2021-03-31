package com.soft.content.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderView.java
 * @Description TODO
 * @createTime 2021年03月31日 18:32:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HbOrderView {
    private String pkOrderId;
    private Timestamp createTime;
    private String goodName;
    private String goodImage;
    private int count;
    private String description;
    private String userName;
    private Double money;
    private int status;

}
