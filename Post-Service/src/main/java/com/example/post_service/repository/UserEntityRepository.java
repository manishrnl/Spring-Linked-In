package com.example.post_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.post_service.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}