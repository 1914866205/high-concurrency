package com.soft.usercenter.repository;

import com.soft.usercenter.model.entity.HbUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUserRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:33:00
 */
public interface HbUserRepository extends JpaRepository<HbUser, String> {

    /**
     * 根据账号查询用户
     * @param username
     * @return
     */
    HbUser findHbUserByUsername(String username);


    /**
     * 根据手机号查询用户
     *
     * @param phone
     * @return
     */
    HbUser findHbUserByPhone(String phone);


}
