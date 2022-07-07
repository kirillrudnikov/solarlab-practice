package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.ConfirmationToken;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenAlreadyUsedException;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenExpiredException;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenNotFoundException;
import com.rudnikov.solarlab.repository.ConfirmationTokenRepository;
import com.rudnikov.solarlab.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken fetchConfirmationToken(String token) {

        if (confirmationTokenRepository.findByToken(token).isEmpty()) {
            throw new ConfirmationTokenNotFoundException("Answer << Confirmation Token not exists!");
        }

        log.warn("Request >> Fetching confirmation token with token = {} from database", token);
        return confirmationTokenRepository.findByToken(token).get();
    }

    public Boolean confirmToken(ConfirmationToken confirmationToken) {

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyUsedException("Answer << Confirmation Token already has been used!");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Answer << Confirmation Token has been expired!");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);

        return true;
    }

}