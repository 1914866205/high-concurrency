package com.soft.content.service.impl;

import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.GoodsDto;
import com.soft.content.model.dto.SearchDto;
import com.soft.content.model.entity.HbGood;
import com.soft.content.repository.HbGoodRepository;
import com.soft.content.service.HbGoodService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGoodService.java
 * @Description TODO
 * @createTime 2021年03月27日 09:47:00
 */
@Service
public class HbGoodServiceImpl implements HbGoodService {
    @Resource
    private HbGoodRepository hbGoodRepository;

    @Override
    public Page<HbGood> findGoodsByContent(SearchDto searchDto) {
        //创建分页构建器，按时间降序
        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getPageSize(), Sort.Direction.DESC, "createdTime");
        //根据内容模糊搜索
        List<HbGood> result = hbGoodRepository.findHbGoodsByGoodNameLikeOrDescriptionLike("%" + searchDto.getContent() + "%", "%" + searchDto.getContent() + "%");
        Page<HbGood> goodPage = new PageImpl<>(result, pageable, result.size());
        return goodPage;
    }

    @Override
    public List<HbGood> findGoodsByUserId(String userId) {
        return hbGoodRepository.findHbGoodsByUserId(userId);
    }

    @Override
    public Page<HbGood> findAllGoods(SearchDto searchDto) {
        //创建分页构建器，按时间降序
        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getPageSize(), Sort.Direction.DESC, "createdTime");
        List<HbGood> result = hbGoodRepository.findAll();
        Page<HbGood> goodPage = new PageImpl<>(result, pageable, result.size());
        return goodPage;
    }

    @Override
    public Map<String, List<HbGood>> findGoodsByType() {
        List<String> allTypes = hbGoodRepository.findAllTypes();
        Map<String, List<HbGood>> listMap = new HashMap();
        for (String type : allTypes) {
            listMap.put(type, hbGoodRepository.findHbGoodsByType(type));
        }
        return listMap;
    }

    @Override
    public List<HbGood> getAllGoods() {
        return hbGoodRepository.findAll();
    }

    @Override
    public List<String> findAllGoodsId() {
        return hbGoodRepository.findAllGoodId();
    }

    @Override
    public HbGood getGoodsById(String goodId) {
        return hbGoodRepository.findHbGoodByPkGoodIdEquals(goodId);
    }

    @Override
    public ResponseResult addGoods(GoodsDto goodsDto) {
        hbGoodRepository.save(HbGood.builder()
    .pkGoodId(UUID.randomUUID().toString().substring(0, 19))
                .type(goodsDto.getType())
                .userId(goodsDto.getPkUserIngId())
                .goodName(goodsDto.getGoodName())
                .price(goodsDto.getPrice())
                .image(goodsDto.getImage())
                .description(goodsDto.getDescription())
                .count(goodsDto.getCount())
                .status(1)
                .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                .updatedTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());
        return ResponseResult.success();
    }

}
