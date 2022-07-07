package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.Comment;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.comment.CommentAlreadyExistsException;
import com.rudnikov.solarlab.exception.comment.CommentNotFoundException;
import com.rudnikov.solarlab.repository.CommentRepository;
import com.rudnikov.solarlab.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> fetchAllComments() {
        log.warn("Request >> Fetching all comments from database");
        return commentRepository.findAll();
    }

    public Comment fetchComment(Long id) {

        if (commentRepository.findById(id).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        log.warn("Request >> Fetching advert by id = {} from database", id);
        return commentRepository.getById(id);
    }

    public Comment saveComment(User author, Comment comment) {

        if (commentRepository.findCommentById(comment.getId()).isPresent()) {
            throw new CommentAlreadyExistsException("Answer >> Comment already exists!");
        }

        comment.setAuthor(author);

        log.warn("Request >> Saving comment with id = {} and parent = {} to database", comment.getId(), comment.getAdvert());
        commentRepository.save(comment);

        return comment;
    }

    public Comment updateComment(Long id, Comment newComment) {
        if (commentRepository.findCommentById(id).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        newComment.setId(id);

        return commentRepository.save(newComment);
    }

    public Boolean deleteComment(Comment comment) {

        if (commentRepository.findById(comment.getId()).isEmpty()) {
            throw new CommentNotFoundException("Answer << Comment not found!");
        }

        log.warn("Request >> Deleting comment with id = {} and parent = {} from database", comment.getId(), comment.getAdvert());
        commentRepository.delete(comment);

        return true;
    }

}