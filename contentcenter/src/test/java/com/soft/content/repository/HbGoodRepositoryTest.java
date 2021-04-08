package com.soft.content.repository;

import com.soft.content.service.HbGoodService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
    private HbGoodRepository hbGoodRepository;
    @Resource
    private HbOrderRepository hbOrderRepository;

    @Test
    void findHbGoodsByGoodNameOrDescriptionLike() throws ParseException {
//        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLikeOrDescriptionLike("%天线%", "%治%"));
//        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLikeOrDescriptionLike("%天线%", "%治%"));
//        System.out.println("111111111111111");
//        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLike("%天线%"));
//        System.out.println("111111111111111");
//        System.out.println(hbGoodRepository.findHbGoodsByDescriptionLike("%治%"));
//        System.out.println(hbGoodService.findGoodsByType());
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf("2021-03-31 14:19")));
        Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            ParsePosition pos = new ParsePosition(8);
            Date currentTime_2 = formatter.parse("2021-04-08 12:32:09", pos);
        System.out.println(hbOrderRepository.findSecKillUserOrder( "1","2021-04-08 12:32:09"));
    }
}