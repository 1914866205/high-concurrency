package com.soft.content.service;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.SearchDto;
import com.soft.content.model.entity.HbGood;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGoodService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:47:00
 */
public interface HbGoodService {
    Page<HbGood> findGoodsByContent(SearchDto searchDto);

    List<HbGood> findGoodsByUserId(String userId);
}
