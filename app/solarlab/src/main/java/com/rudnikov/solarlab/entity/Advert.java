package com.rudnikov.solarlab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adverts")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "advert_author")
    private User author;
    private String title;
    private String content;
    private String category;
    private String region;

    @OneToMany(mappedBy = "advert", orphanRemoval = true)
    @JsonManagedReference(value = "parent_advert")
    private List<Comment> comments;

    public Advert(User author, String title, String content, String category, String region) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.category = category;
        this.region = region;
    }

}