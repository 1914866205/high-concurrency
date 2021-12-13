package com.soft.usercenter.repository;


import com.soft.usercenter.model.entity.HbOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:35:00
 */
public interface HbOrderRepository extends JpaRepository<HbOrder,String> {
}
