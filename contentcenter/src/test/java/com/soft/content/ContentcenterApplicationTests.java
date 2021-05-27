package com.soft.content;

import com.soft.content.model.entity.HbOrder;
import com.soft.content.repository.HbOrderRepository;
import com.soft.content.repository.HbStrategyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class ContentcenterApplicationTests {
    @Resource
    private HbOrderRepository hbOrderRepository;

    @Test
    void contextLoads() {
        HbOrder build = HbOrder.builder().pkOrderId("d7c61318-ee80-4652-")
                .pkGoodId("1").ranked(3484).userId("1")
                .phone("18851855106").state(0).number(0)
                .createdTime(Timestamp.valueOf("2021-05-27 14:19:12"))
                .updatedTime(Timestamp.valueOf("2021-03-29 17:32:17"))
                .build();
        System.out.println(build);
        System.out.println(hbOrderRepository.save(build));

    }

}
