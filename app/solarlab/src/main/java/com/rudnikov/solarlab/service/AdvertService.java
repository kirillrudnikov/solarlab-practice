package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.AdvertEntity;
import com.rudnikov.solarlab.entity.UserEntity;

import java.util.List;

public interface AdvertService {

    List<AdvertEntity> fetchAllAdverts();
    AdvertEntity fetchAdvert(Long id);
    AdvertEntity saveAdvert(UserEntity author, AdvertEntity advertEntity);
    AdvertEntity updateAdvert(Long id, AdvertEntity newAdvertEntity);
    Boolean deleteAdvert(AdvertEntity advertEntity);

}