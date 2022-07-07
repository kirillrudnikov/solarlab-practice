package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.User;

import java.util.List;

public interface UserService {

    List<User> fetchAllUsers();
    User fetchUser(Long id);
    User saveUser(User user);
    User updateUser(Long id, User newUser);
    Boolean deleteUser(User user);

}