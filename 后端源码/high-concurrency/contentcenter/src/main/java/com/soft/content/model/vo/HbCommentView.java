package com.soft.content.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author crq
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HbCommentView {
    private String pkCommentId;
    private String pkGoodId;
    private String pkUserIngId;
    private String pkUserEdId;
    private int type;
    private String content;
    private Timestamp createdTime;
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
}
