package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.request.SignupRequest;

public interface RegistrationService {

    String createNewAccount(SignupRequest request);
    Boolean confirmRegistration(String token);

}