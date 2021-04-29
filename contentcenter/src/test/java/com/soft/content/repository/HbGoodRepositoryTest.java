package com.soft.content.repository;


import com.soft.content.model.dto.SearchDto;
import com.soft.content.service.HbGoodService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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
    @Test
    public void testRedisCluster() {
        System.out.println(hbGoodService.findAllGoods(SearchDto.builder().pageSize(10).currentPage(0).content("S").build()));
    }
}