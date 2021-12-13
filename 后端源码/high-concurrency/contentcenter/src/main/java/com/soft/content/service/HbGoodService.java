package com.soft.content.service;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.AllGoodsDto;
import com.soft.content.model.dto.GoodsDto;
import com.soft.content.model.dto.SearchDto;
import com.soft.content.model.entity.HbGood;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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

    Page<AllGoodsDto> findAllGoods(SearchDto searchDto);

    Map<String, List<HbGood>> findGoodsByType();

    List<HbGood> getAllGoods();

    List<String> findAllGoodsId();

    HbGood getGoodsById(String goodId);

    /**
     * 发布商品
     * @param goodsDto
     * @return
     */
    ResponseResult addGoods(GoodsDto goodsDto);
}
