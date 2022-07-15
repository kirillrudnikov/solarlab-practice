package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.AdvertEntity;
import com.rudnikov.solarlab.entity.UserEntity;
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
@Service @Transactional
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;

    public List<AdvertEntity> fetchAllAdverts() {
        log.warn("Request >> Fetching all adverts from database");
        return advertRepository.findAll();
    }

    public AdvertEntity fetchAdvert(Long id) {

        if (advertRepository.findById(id).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        log.warn("Request >> Fetching advert by id = {} from database", id);
        return advertRepository.getById(id);
    }

    public AdvertEntity saveAdvert(UserEntity author, AdvertEntity advertEntity) {

        if (advertRepository.findById(advertEntity.getId()).isPresent()) {
            throw new AdvertAlreadyExistsException("Answer << Advert already exists!");
        }

        advertEntity.setAuthor(author);

        log.warn("Request >> Saving advert with id = {} and author = {} to database", advertEntity.getId(), advertEntity.getAuthor());
        return advertRepository.save(advertEntity);
    }

    public AdvertEntity updateAdvert(Long id, AdvertEntity newAdvertEntity) {

        if (advertRepository.findAdvertById(id).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        newAdvertEntity.setId(id);

        return advertRepository.save(newAdvertEntity);

    }

    public Boolean deleteAdvert(AdvertEntity advertEntity) {

        if (advertRepository.findById(advertEntity.getId()).isEmpty()) {
            throw new AdvertNotFoundException("Answer << Advert not found!");
        }

        log.warn("Request >> Deleting advert with id = {} from database", advertEntity.getId());
        advertRepository.delete(advertEntity);

        return true;
    }

}