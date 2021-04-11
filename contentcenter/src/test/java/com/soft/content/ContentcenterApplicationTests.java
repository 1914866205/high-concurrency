package com.soft.content;

import com.soft.content.repository.HbStrategyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ContentcenterApplicationTests {
    @Resource
    private HbStrategyRepository hbStrategyRepository;
    @Test
    void contextLoads() {
        System.out.println(hbStrategyRepository.findAll());
    }

}
