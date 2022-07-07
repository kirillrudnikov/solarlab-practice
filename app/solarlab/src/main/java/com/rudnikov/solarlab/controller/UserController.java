package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.user.UserAlreadyExistsException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.service.UserService;
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
public class UserController {

    private final UserService userService;

    // Fetch all Users from DB
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> fetchAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchAllUsers());
    }

    // Fetch User from DB by ID
    @RequestMapping(value = "/user/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUser(id));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Save User to DB
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(userService.saveUser(user));
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Update User from DB
    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, newUser));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Delete User from DB
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userService.fetchUser(id)));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

}