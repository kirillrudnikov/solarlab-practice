package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.user.UserAlreadyExistsException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.repository.UserRepository;
import com.rudnikov.solarlab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<User> fetchAllUsers() {
        log.warn("Request >> Fetching all users from database");
        return userRepository.findAll();
    }

    public User fetchUser(Long id) {

        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        log.warn("Request >> Fetching user by id = {} from database", id);
        return userRepository.findUserById(id).get();
    }

    public User saveUser(User user) {

        if (userRepository.findById(user.getId()).isPresent()) {
            throw new UserAlreadyExistsException("Answer << User already exists!");
        }

        log.warn("Request >> Saving user {} to database", user.getEmail());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User newUser) {

        if (userRepository.findUserById(id).isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        newUser.setId(id);

        return userRepository.save(newUser);

    }

    public Boolean deleteUser(User user) {

        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        log.warn("Request >> Deleting user {} from database", user.getEmail());
        userRepository.delete(user);

        return true;
    }

}