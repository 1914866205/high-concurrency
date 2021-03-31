package com.soft.usercenter.service.impl;

import com.soft.usercenter.model.entity.HbUser;
import com.soft.usercenter.repository.HbUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUserServiceImplTest.java
 * @Description TODO
 * @createTime 2021年03月29日 16:01:00
 */
@SpringBootTest
class HbUserServiceImplTest {
    @Resource
    private HbUserRepository hbUserRepository;
    @Test
    void edit() {
        Optional<HbUser> byId = hbUserRepository.findById("1");
        System.out.println("-----------------------");
        System.out.println(byId.get());
        System.out.println("-----------------------");

    }
}