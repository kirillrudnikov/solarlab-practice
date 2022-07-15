package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.ConfirmationTokenEntity;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenAlreadyUsedException;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenExpiredException;
import com.rudnikov.solarlab.exception.confirmationtoken.ConfirmationTokenNotFoundException;
import com.rudnikov.solarlab.repository.ConfirmationTokenRepository;
import com.rudnikov.solarlab.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service @Transactional
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenEntity fetchConfirmationToken(String token) {

        if (confirmationTokenRepository.findByToken(token).isEmpty()) {
            throw new ConfirmationTokenNotFoundException("Answer << Confirmation Token not exists!");
        }

        log.warn("Request >> Fetching confirmation token with token = {} from database", token);
        return confirmationTokenRepository.findByToken(token).get();
    }

    public Boolean confirmToken(ConfirmationTokenEntity confirmationTokenEntity) {

        if (confirmationTokenEntity.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyUsedException("Answer << Confirmation Token already has been used!");
        }

        LocalDateTime expiresAt = confirmationTokenEntity.getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Answer << Confirmation Token has been expired!");
        }

        confirmationTokenEntity.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationTokenEntity);

        return true;
    }

}