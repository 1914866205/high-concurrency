package com.soft.content;

import com.soft.content.repository.HbOrderRepository;
import com.soft.content.repository.HbStrategyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ContentcenterApplicationTests {
    @Resource
    private HbOrderRepository hbOrderRepository;
    @Test
    void contextLoads() {
        System.out.println( hbOrderRepository.findSecKillUserOrder("1","2021-01-12 13:33:52.0" ));
    }

}
