package com.soft.usercenter.repository;

import com.soft.usercenter.model.entity.HbComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbCommentRepository.java
 * @Description TODO
 * @createTime 2021年03月27日 09:38:00
 */
public interface HbCommentRepository extends JpaRepository<HbComment, String> {

}
