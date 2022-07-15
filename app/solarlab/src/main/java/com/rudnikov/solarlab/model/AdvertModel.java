package com.rudnikov.solarlab.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class AdvertModel {

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private Long id;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "authorId")
    private UserModel author;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String title;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String content;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String region;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String category;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    private String subCategory;

    @JsonView(value = {JsonViewClass.Anonymous.class})
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty(value = "commentsId")
    private List<CommentModel> comments;

}