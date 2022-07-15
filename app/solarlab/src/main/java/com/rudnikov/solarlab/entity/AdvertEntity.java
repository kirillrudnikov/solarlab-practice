package com.rudnikov.solarlab.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "adverts")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class AdvertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity author;
    private String title;
    private String content;
    private String region;
    private String category;
    private String subCategory;

    @OneToMany(mappedBy = "advert", orphanRemoval = true)
    private List<CommentEntity> comments;

}