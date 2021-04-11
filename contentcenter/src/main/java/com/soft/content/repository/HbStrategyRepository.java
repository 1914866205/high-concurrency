package com.soft.content.repository;

import com.soft.content.model.entity.HbStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbStrategyRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:37:00
 */
public interface HbStrategyRepository extends JpaRepository<HbStrategy, String> {

    int deleteHbStrategiesByGoodIdEquals(String goodId);

}
