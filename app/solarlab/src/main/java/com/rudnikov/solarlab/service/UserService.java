package com.rudnikov.solarlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.rudnikov.solarlab.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserEntity> fetchAllUsers();
    UserEntity fetchUser(Long id);
    UserEntity saveUser(UserEntity userEntity);
    UserEntity updateUser(Long id, UserEntity newUserEntity);
    UserEntity applyPatch(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;
    Boolean deleteUser(Long id);

}