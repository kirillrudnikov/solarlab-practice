package com.rudnikov.solarlab.repository;

import com.rudnikov.solarlab.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserById(Long id);
    Optional<UserEntity> findUserByUsernameOrEmail(String username, String email);

}