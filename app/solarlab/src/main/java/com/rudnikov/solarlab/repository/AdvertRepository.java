package com.rudnikov.solarlab.repository;

import com.rudnikov.solarlab.entity.AdvertEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {

    List<AdvertEntity> findAll();
    List<AdvertEntity> findAllByAuthor(UserEntity author);
    Optional<AdvertEntity> findAdvertById(Long id);
    Optional<AdvertEntity> findAdvertByTitle(String title);

    //Optional<Advert> findAdvertByParentCategory(ParentAdvertCategory parentAdvertCategory);

}