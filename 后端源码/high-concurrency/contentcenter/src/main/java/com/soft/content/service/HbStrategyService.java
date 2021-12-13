package com.soft.content.service;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.SecKillAddDto;
import com.soft.content.model.entity.HbStrategy;

import java.text.ParseException;
import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbStrategyService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:49:00
 */
public interface HbStrategyService {
    List<HbStrategy> findAllStrategy();

    ResponseResult addStrategy(SecKillAddDto secKillAddDto) throws ParseException;

    ResponseResult getStrategy(String goodId);
}
