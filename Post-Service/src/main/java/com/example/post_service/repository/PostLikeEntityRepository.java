package com.example.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.post_service.entity.PostLikeEntity;

public interface PostLikeEntityRepository extends JpaRepository<PostLikeEntity, Long> {
}