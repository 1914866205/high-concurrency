package com.soft.content.repository;

import com.soft.content.model.entity.HbGood;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有商品类型
     *
     * @return
     */
    @Query(value = "select distinct  type from  HbGood ")
    List<String> findAllTypes();

    /**
     * 根据商品类型查询商品
     * @param type
     * @return
     */
    List<HbGood> findHbGoodsByType(String type);

    /**
     * 根据商品ID查商品
     * @param pkGoodId
     * @return
     */
    HbGood findHbGoodByPkGoodIdEquals(String pkGoodId);
}
