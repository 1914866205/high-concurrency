package com.soft.content.repository;

import com.soft.content.model.entity.HbComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbCommentRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:38:00
 */
public interface HbCommentRepository extends JpaRepository<HbComment, String> {

    /**
     * 根据商品Id查询所有评论
     * @param pkGoodId
     * @return
     */
    List<HbComment> findHbCommentsByPkGoodId(String pkGoodId);

}
