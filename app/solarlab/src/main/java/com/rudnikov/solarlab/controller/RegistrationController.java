package com.rudnikov.solarlab.controller;

import com.rudnikov.solarlab.request.SignupRequest;
import com.rudnikov.solarlab.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> createNewAccount(@RequestBody SignupRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(registrationService.createNewAccount(request));
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    @RequestMapping(value = "/registration/confirm", method = RequestMethod.POST)
    public ResponseEntity<?> confirmRegistration(@RequestParam(name = "token") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(registrationService.confirmRegistration(token));
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

}