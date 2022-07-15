package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.exception.comment.CommentAlreadyExistsException;
import com.rudnikov.solarlab.exception.comment.CommentNotFoundException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.model.CommentModel;
import com.rudnikov.solarlab.model.mapper.CommentMapper;
import com.rudnikov.solarlab.model.mapper.CycleAvoidingMappingContext;
import com.rudnikov.solarlab.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @RequestMapping(value = "/api")
@AllArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @RequestMapping(
            value = "/comments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAllComments() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentMapper.castCommentEntityCollectionToModelList
                        (commentService.fetchAllComments(), new CycleAvoidingMappingContext()));
    }

    @RequestMapping(
            value = "/comment/get/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUser(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.fetchComment(id));
        } catch (CommentNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/comment/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveComment(@RequestBody UserEntity author, @RequestBody CommentModel commentModel) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            CommentModel view = commentMapper
                    .castCommentEntityToModel(commentService.saveComment
                            (author, commentMapper.castCommentModelToEntity
                                    (commentModel, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(uri).body(view);
        } catch (CommentAlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/comment/update/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CommentEntity newCommentEntity) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.updateComment(id, newCommentEntity));
        } catch (UserNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    //patch

    @RequestMapping(
            value = "/comment/delete/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentService.deleteComment(commentService.fetchComment(id)));
        } catch (CommentNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

}