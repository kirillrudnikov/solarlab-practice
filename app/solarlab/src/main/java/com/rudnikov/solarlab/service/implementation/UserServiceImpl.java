package com.rudnikov.solarlab.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.entity.UserRole;
import com.rudnikov.solarlab.exception.UnAuthorizedActionException;
import com.rudnikov.solarlab.exception.user.UserAlreadyExistsException;
import com.rudnikov.solarlab.exception.user.UserNotFoundException;
import com.rudnikov.solarlab.repository.UserRepository;
import com.rudnikov.solarlab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service @Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserEntity> fetchAllUsers() {
        log.warn("Request >> Fetching all users from database");
        return userRepository.findAll();
    }

    public UserEntity fetchUser(Long id) {

        Optional<UserEntity> userFromDb = userRepository.findById(id);

        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        log.warn("Request >> Fetching user by id = {} from database", id);
        return userFromDb.get();
    }

    public UserEntity saveUser(UserEntity userEntity) {

        if (userRepository.findById(userEntity.getId()).isPresent()) {
            throw new UserAlreadyExistsException("Answer << User already exists!");
        }

        log.warn("Request >> Saving user {} to database", userEntity.getEmail());
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(Long id, UserEntity newUserEntity) {

        Optional<UserEntity> userFromDb = userRepository.findUserById(id);

        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        doIfHasEnoughRights(userFromDb.get());

        newUserEntity.setId(id);

        if (!userFromDb.get().getAdverts().isEmpty()) {
            newUserEntity.setAdverts(userFromDb.get().getAdverts());
        } else {
            newUserEntity.setAdverts(new ArrayList<>());
        }

        if (!userFromDb.get().getComments().isEmpty()) {
            newUserEntity.setComments(userFromDb.get().getComments());
        } else {
            newUserEntity.setComments(new ArrayList<>());
        }

        return userRepository.save(newUserEntity);

    }

    public UserEntity applyPatch(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {

        Optional<UserEntity> userFromDb = userRepository.findUserById(id);

        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        doIfHasEnoughRights(userFromDb.get());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode patchedUser = patch.apply(mapper.convertValue(userFromDb.get(), JsonNode.class));

        return mapper.treeToValue(patchedUser, UserEntity.class);

    }

    public Boolean deleteUser(Long id) {

        Optional<UserEntity> userFromDb = userRepository.findUserById(id);

        if (userFromDb.isEmpty()) {
            throw new UserNotFoundException("Answer << User not found!");
        }

        doIfHasEnoughRights(userFromDb.get());

        userRepository.delete(userFromDb.get());

        return true;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<UserEntity> userDetails = userRepository.findUserByUsernameOrEmail(login, login);

        if (userDetails.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return userDetails.get();
    }

    private void doIfHasEnoughRights(UserEntity userEntity) {

        UserDetails authorizedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean isAdmin = authorizedUser.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMINISTRATOR.getAuthority()));

        if (!authorizedUser.getUsername().equals(userEntity.getUsername()) & !isAdmin) {
            throw new UnAuthorizedActionException("Answer << You can't delete someone else's");
        }

    }

}