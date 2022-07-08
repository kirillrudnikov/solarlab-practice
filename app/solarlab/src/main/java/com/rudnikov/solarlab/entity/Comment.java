package com.rudnikov.solarlab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "advert_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = false)
    @JsonBackReference
    private Advert advert;

    public Comment(User author, String title, String content, Advert advert) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.advert = advert;
    }

}