package com.example.user_service.repository;

import com.example.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    String email(String email);

    boolean existsByEmail(String email);
}