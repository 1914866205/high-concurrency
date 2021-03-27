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

}
