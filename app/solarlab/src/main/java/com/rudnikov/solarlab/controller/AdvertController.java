package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.exception.advert.AdvertAlreadyExistsException;
import com.rudnikov.solarlab.exception.advert.AdvertNotFoundException;
import com.rudnikov.solarlab.model.AdvertModel;
import com.rudnikov.solarlab.model.mapper.AdvertMapper;
import com.rudnikov.solarlab.model.mapper.CycleAvoidingMappingContext;
import com.rudnikov.solarlab.service.AdvertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @RequestMapping(value = "/api")
@AllArgsConstructor
public class AdvertController {

    private final AdvertMapper advertMapper;
    private final AdvertService advertService;

    @RequestMapping(
            value = "/adverts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAllAdverts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(advertMapper.castAdvertEntityCollectionToModelList
                        (advertService.fetchAllAdverts(), new CycleAvoidingMappingContext()));
    }

    @RequestMapping(
            value = "/advert/get/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAdvert(@PathVariable Long id) {
        try {
            AdvertModel view = advertMapper
                    .castAdvertEntityToModel(advertService.fetchAdvert(id), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(view);
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/advert/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveUser(@RequestBody AdvertModel advertModel, @RequestBody UserEntity author) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            AdvertModel view = advertMapper
                    .castAdvertEntityToModel(advertService.saveAdvert
                            (author, advertMapper.castAdvertModelToEntity
                                    (advertModel, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(uri)
                    .body(view);
        } catch (AdvertAlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/advert/update/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody AdvertModel newAdvertModel) {
        try {
            AdvertModel view = advertMapper
                    .castAdvertEntityToModel(advertService.updateAdvert
                            (id, advertMapper.castAdvertModelToEntity
                                    (newAdvertModel, new CycleAvoidingMappingContext())), new CycleAvoidingMappingContext());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(view);
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    //patch

    @RequestMapping(
            value = "/advert/delete/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(advertService.deleteAdvert(advertService.fetchAdvert(id)));
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

}