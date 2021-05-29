package com.soft.content.repository;


import com.soft.content.common.ResponseResult;
import com.soft.content.model.dto.RuleDto;
import com.soft.content.model.dto.SearchDto;
import com.soft.content.model.dto.RuleDto;
import com.soft.content.model.dto.SecKillAddDto;
import com.soft.content.model.dto.SecResultDto;
import com.soft.content.model.entity.HbStrategy;
import com.soft.content.service.HbGoodService;
import com.soft.content.service.HbOrderService;
import com.soft.content.service.HbStrategyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGoodRepositoryTest.java
 * @Description TODO
 * @createTime 2021年03月29日 17:44:00
 */
@SpringBootTest
class HbGoodRepositoryTest {
    @Resource
    private HbGoodService hbGoodService;
    @Resource
    private HbStrategyService hbStrategyService;
    @Resource
    private HbOrderService hbOrderService;

    @Test
    public void testRedisCluster() {
        SecResultDto secResultDto = new SecResultDto();
        secResultDto.setTime("2021-04-28 19:31:02");
        secResultDto.setGoodId("1");
        hbStrategyService.getStrategy("1").getData();
        SecKillAddDto data = (SecKillAddDto) hbStrategyService.getStrategy("1").getData();
        List<RuleDto> ruleDtoList = data.getRuleDtoList();
        List<HbStrategy> strategies = new ArrayList<>();
        for (RuleDto ruleDto : ruleDtoList) {
            HbStrategy hbStrategy = new HbStrategy();
            hbStrategy.setRankStart(ruleDto.getStart());
            hbStrategy.setRankEnd(ruleDto.getEnd());
            hbStrategy.setDiscount((double) (ruleDto.getDiscount() / 10));
            strategies.add(hbStrategy);
        }
        secResultDto.setStrategies(strategies);

        System.out.println(secResultDto);

        System.out.println(hbOrderService.findSecKillUserOrder(secResultDto));
    }
}