package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.entity.Advert;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.exception.advert.AdvertAlreadyExistsException;
import com.rudnikov.solarlab.exception.advert.AdvertNotFoundException;
import com.rudnikov.solarlab.service.AdvertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    // Fetch all Users from DB
    @RequestMapping(value = "/adverts", method = RequestMethod.GET)
    public ResponseEntity<List<Advert>> fetchAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(advertService.fetchAllAdverts());
    }

    // Fetch Advert from DB by ID
    @RequestMapping(value = "/advert/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAdvert(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(advertService.fetchAdvert(id));
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Save Advert to DB
    @RequestMapping(value = "/advert/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody Advert advert, @RequestBody User author) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(advertService.saveAdvert(author, advert));
        } catch (AdvertAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    @RequestMapping(value = "/advert/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Advert newAdvert) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(advertService.updateAdvert(id, newAdvert));
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    // Delete Advert from DB
    @RequestMapping(value = "/advert/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(advertService.deleteAdvert(advertService.fetchAdvert(id)));
        } catch (AdvertNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

}