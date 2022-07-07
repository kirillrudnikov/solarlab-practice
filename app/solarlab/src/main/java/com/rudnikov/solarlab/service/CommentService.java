package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.Comment;
import com.rudnikov.solarlab.entity.User;

import java.util.List;

public interface CommentService {

    List<Comment> fetchAllComments();
    Comment fetchComment(Long id);
    Comment saveComment(User author, Comment comment);
    Comment updateComment(Long id, Comment newComment);
    Boolean deleteComment(Comment comment);

}