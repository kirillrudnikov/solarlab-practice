package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.request.SignupRequest;
import com.rudnikov.solarlab.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createNewAccount(@RequestBody SignupRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(registrationService.createNewAccount(request));
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

    @RequestMapping(
            value = "/signup/confirm",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> confirmRegistration(@RequestParam(name = "token") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(registrationService.confirmRegistration(token));
        } catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase() + exception.getMessage());
        }
    }

}