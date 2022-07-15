package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.exception.comment.CommentAlreadyExistsException;
import com.rudnikov.solarlab.exception.comment.CommentNotFoundException;
import com.rudnikov.solarlab.repository.CommentRepository;
import com.rudnikov.solarlab.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service @Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public List<CommentEntity> fetchAllComments() {
        log.warn("Request >> Fetching all comments from database");
        return commentRepository.findAll();
    }

    public CommentEntity fetchComment(Long id) {

        if (commentRepository.findById(id).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        log.warn("Request >> Fetching advert by id = {} from database", id);
        return commentRepository.getById(id);
    }

    public CommentEntity saveComment(UserEntity author, CommentEntity commentEntity) {

        if (commentRepository.findCommentById(commentEntity.getId()).isPresent()) {
            throw new CommentAlreadyExistsException("Answer >> Comment already exists!");
        }

        commentEntity.setAuthor(author);

        log.warn("Request >> Saving comment with id = {} and parent = {} to database", commentEntity.getId(), commentEntity.getAdvert());
        commentRepository.save(commentEntity);

        return commentEntity;
    }

    public CommentEntity updateComment(Long id, CommentEntity newCommentEntity) {
        if (commentRepository.findCommentById(id).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        newCommentEntity.setId(id);

        return commentRepository.save(newCommentEntity);
    }

    public Boolean deleteComment(CommentEntity commentEntity) {

        if (commentRepository.findById(commentEntity.getId()).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        log.warn("Request >> Deleting comment with id = {} and parent = {} from database", commentEntity.getId(), commentEntity.getAdvert());
        commentRepository.delete(commentEntity);

        return true;
    }

}