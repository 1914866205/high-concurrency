package com.soft.content.service.impl;

import com.soft.content.model.entity.HbStrategy;
import com.soft.content.repository.HbStrategyRepository;
import com.soft.content.service.HbStrategyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbStrategyServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:49:00
 */
@Service
public class HbStrategyServiceImpl implements HbStrategyService {
    @Resource
    private HbStrategyRepository hbStrategyRepository;

    @Override
    public List<HbStrategy> findAllStrategy() {
        return hbStrategyRepository.findAll();
    }
}
