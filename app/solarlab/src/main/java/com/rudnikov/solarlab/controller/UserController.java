package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.exception.UnAuthorizedActionException;
import com.rudnikov.solarlab.exception.user.UserAlreadyExistsException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.model.UserModel;
import com.rudnikov.solarlab.model.mapper.CycleAvoidingMappingContext;
import com.rudnikov.solarlab.model.mapper.UserMapper;
import com.rudnikov.solarlab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.PreUpdate;
import java.net.URI;

@RestController @RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> fetchAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.castUserEntityCollectionToModelList
                        (userService.fetchAllUsers(), new CycleAvoidingMappingContext()));
    }

    @RequestMapping(
            value = "/user/get/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUser(@PathVariable Long id) {
        try {
            UserModel view = userMapper
                    .castUserEntityToModel(userService.fetchUser(id), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(view);
        } catch (UserNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/user/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveUser(@RequestBody UserModel userModel) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            UserModel view = userMapper
                    .castUserEntityToModel(userService.saveUser
                            (userMapper.castUserModelToEntity
                                    (userModel, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(uri)
                    .body(view);
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/user/update/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        try {
            UserEntity userEntity = userMapper.castUserModelToEntity(userModel, new CycleAvoidingMappingContext());
            UserModel view = userMapper
                    .castUserEntityToModel(userService.updateUser(id, userEntity), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(view);
        } catch (UserNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    // TODO: implement
//    @RequestMapping(
//            value = "/user/patch/{id}",
//            method = RequestMethod.PATCH,
//            consumes = PatchMediaType.APPLICATION_JSON_PATCH_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody JsonPatch jsonPatch) {
//        try {
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(userService.updateUser(id, userService.applyPatch(id, jsonPatch)));
//        } catch (JsonPatchException | JsonProcessingException exception) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
//        }
//    }

    @RequestMapping(
            value = "/user/delete/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.deleteUser(id));
        } catch (UserNotFoundException | UnAuthorizedActionException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

}