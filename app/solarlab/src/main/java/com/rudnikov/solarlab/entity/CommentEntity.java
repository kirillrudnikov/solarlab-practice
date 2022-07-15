package com.rudnikov.solarlab.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "advert_comments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity author;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private AdvertEntity advert;

    public CommentEntity(UserEntity author, String title, String content, AdvertEntity advert) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.advert = advert;
    }

}