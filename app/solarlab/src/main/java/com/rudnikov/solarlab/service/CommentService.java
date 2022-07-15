package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.entity.UserEntity;

import java.util.List;

public interface CommentService {

    List<CommentEntity> fetchAllComments();
    CommentEntity fetchComment(Long id);
    CommentEntity saveComment(UserEntity author, CommentEntity commentEntity);
    CommentEntity updateComment(Long id, CommentEntity newCommentEntity);
    Boolean deleteComment(CommentEntity commentEntity);

}