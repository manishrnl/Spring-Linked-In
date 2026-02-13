package com.example.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.post_service.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByUserId(Long userId);
}