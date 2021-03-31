package com.soft.content.repository;


import com.soft.content.model.entity.HbOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbOrderRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:35:00
 */
public interface HbOrderRepository extends JpaRepository<HbOrder, String> {
    /**
     * 根据用户Id查询用户所有订单
     * @param userId
     * @return
     */
    List<HbOrder> findHbOrdersByUserIdEquals(String userId);
}
