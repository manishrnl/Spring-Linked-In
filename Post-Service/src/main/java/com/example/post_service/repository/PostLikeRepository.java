package com.example.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.post_service.entity.PostLikeEntity;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Transactional
    void deleteByUserIdAndPostId(long userId, Long postId);


    boolean existsByPostId(Long postId);
}
