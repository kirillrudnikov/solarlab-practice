package com.rudnikov.solarlab.repository;

import com.rudnikov.solarlab.entity.Advert;
import com.rudnikov.solarlab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    List<Advert> findAll();
    List<Advert> findAllByAuthor(User author);
    Optional<Advert> findAdvertById(Long id);
    Optional<Advert> findAdvertByTitle(String title);

}