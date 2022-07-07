package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.ConfirmationToken;

public interface ConfirmationTokenService {

    ConfirmationToken fetchConfirmationToken(String token);
    Boolean confirmToken(ConfirmationToken confirmationToken);

}