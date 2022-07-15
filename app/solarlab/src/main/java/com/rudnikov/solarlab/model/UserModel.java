package com.rudnikov.solarlab.model;

import com.fasterxml.jackson.annotation.*;
import com.rudnikov.solarlab.entity.UserRole;
import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data @Builder
public class UserModel {

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private Long id;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String username;

    @JsonView(value = {JsonViewClass.User.class})
    private String email;

    @JsonView(value = {JsonViewClass.User.class})
    private String phoneNumber;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private String password;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private UserRole role;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private Boolean isAccountNonExpired;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private Boolean isAccountNonLocked;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private Boolean isCredentialsNonExpired;

    @JsonView(value = {JsonViewClass.Administrator.class})
    private Boolean isEnabled;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "advertsId")
    private List<AdvertModel> adverts;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "commentsId")
    private List<CommentModel> comments;

}