package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> fetchAllUsers();
    User fetchUser(Long id);
    User saveUser(User user);
    User updateUser(Long id, User newUser);
    Boolean deleteUser(User user);

}