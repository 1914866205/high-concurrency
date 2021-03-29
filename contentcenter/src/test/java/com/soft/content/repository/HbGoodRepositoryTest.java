package com.soft.content.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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

    @Test
    void findHbGoodsByGoodNameOrDescriptionLike() {
        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLikeOrDescriptionLike("%天线%", "%治%"));
        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLikeOrDescriptionLike("%天线%", "%治%"));
//        System.out.println("111111111111111");
//        System.out.println(hbGoodRepository.findHbGoodsByGoodNameLike("%天线%"));
//        System.out.println("111111111111111");
//        System.out.println(hbGoodRepository.findHbGoodsByDescriptionLike("%治%"));
//        System.out.println("111111111111111");
    }
}