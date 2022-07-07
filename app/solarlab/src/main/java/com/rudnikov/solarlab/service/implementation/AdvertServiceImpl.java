package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.Advert;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.advert.AdvertAlreadyExistsException;
import com.rudnikov.solarlab.exception.advert.AdvertNotFoundException;
import com.rudnikov.solarlab.repository.AdvertRepository;
import com.rudnikov.solarlab.service.AdvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;

    public List<Advert> fetchAllAdverts() {
        log.warn("Request >> Fetching all adverts from database");
        return advertRepository.findAll();
    }

    public Advert fetchAdvert(Long id) {

        if (advertRepository.findById(id).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        log.warn("Request >> Fetching advert by id = {} from database", id);
        return advertRepository.getById(id);
    }

    public Advert saveAdvert(User author, Advert advert) {

        if (advertRepository.findById(advert.getId()).isPresent()) {
            throw new AdvertAlreadyExistsException("Answer << Advert already exists!");
        }

        advert.setAuthor(author);

        log.warn("Request >> Saving advert with id = {} and author = {} to database", advert.getId(), advert.getAuthor());
        return advertRepository.save(advert);
    }

    public Advert updateAdvert(Long id, Advert newAdvert) {

        if (advertRepository.findAdvertById(id).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        newAdvert.setId(id);

        return advertRepository.save(newAdvert);

    }

    public Boolean deleteAdvert(Advert advert) {

        if (advertRepository.findById(advert.getId()).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        log.warn("Request >> Deleting advert with id = {} from database", advert.getId());
        advertRepository.delete(advert);

        return true;
    }

}