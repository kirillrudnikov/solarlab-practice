package com.rudnikov.solarlab.repository;

import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAll();
    List<CommentEntity> findAllByAuthor(UserEntity author);
    Optional<CommentEntity> findCommentById(Long id);
    Optional<CommentEntity> findCommentByTitle(String title);

}