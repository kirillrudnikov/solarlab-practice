package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.Advert;
import com.rudnikov.solarlab.entity.User;

import java.util.List;

public interface AdvertService {

    List<Advert> fetchAllAdverts();
    Advert fetchAdvert(Long id);
    Advert saveAdvert(User author, Advert advert);
    Advert updateAdvert(Long id, Advert newAdvert);
    Boolean deleteAdvert(Advert advert);

}