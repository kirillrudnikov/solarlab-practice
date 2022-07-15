package com.rudnikov.solarlab.service;

import com.rudnikov.solarlab.entity.ConfirmationTokenEntity;

public interface ConfirmationTokenService {

    ConfirmationTokenEntity fetchConfirmationToken(String token);
    Boolean confirmToken(ConfirmationTokenEntity confirmationTokenEntity);

}