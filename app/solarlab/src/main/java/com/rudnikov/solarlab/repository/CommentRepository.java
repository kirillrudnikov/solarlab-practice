package com.rudnikov.solarlab.repository;

import com.rudnikov.solarlab.entity.Comment;
import com.rudnikov.solarlab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();
    List<Comment> findAllByAuthor(User author);
    Optional<Comment> findCommentById(Long id);
    Optional<Comment> findCommentByTitle(String title);

}