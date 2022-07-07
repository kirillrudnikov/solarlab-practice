package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.Comment;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.comment.CommentAlreadyExistsException;
import com.rudnikov.solarlab.exception.comment.CommentNotFoundException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Fetch all Comments from DB
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> fetchAllComments() {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.fetchAllComments());
    }

    // Fetch Comment from DB by ID
    @RequestMapping(value = "/comment/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.fetchComment(id));
        } catch (CommentNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Save Comment to DB
    @RequestMapping(value = "/comment/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveComment(@RequestBody User author, @RequestBody Comment comment) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(commentService.saveComment(author, comment));
        } catch (CommentAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Update Comment from DB
    @RequestMapping(value = "/comment/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Comment newComment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, newComment));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Delete Comment from DB
    @RequestMapping(value = "/comment/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentService.fetchComment(id)));
        } catch (CommentNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

}