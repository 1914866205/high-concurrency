package com.soft.content.repository;

import com.soft.content.model.entity.HbGood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbGoodRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:34:00
 */
public interface HbGoodRepository extends JpaRepository<HbGood, String> {
    /**
     * 根据商品名称或者描述查询商品
     *
     * @param goodName
     * @param description
     * @return
     */
    List<HbGood> findHbGoodsByGoodNameLikeOrDescriptionLike(String goodName, String description);
    List<HbGood> findHbGoodsByGoodNameLike(String goodName);
    List<HbGood> findHbGoodsByDescriptionLike(String description);

    /**
     * 根据用户Id查询商品
     * @param userId
     * @return
     */
    List<HbGood> findHbGoodsByUserId(String userId);
}
