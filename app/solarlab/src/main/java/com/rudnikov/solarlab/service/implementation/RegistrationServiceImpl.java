package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.ConfirmationTokenEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.entity.UserRole;
import com.rudnikov.solarlab.repository.ConfirmationTokenRepository;
import com.rudnikov.solarlab.repository.UserRepository;
import com.rudnikov.solarlab.request.SignupRequest;
import com.rudnikov.solarlab.service.ConfirmationTokenService;
import com.rudnikov.solarlab.service.EmailService;
import com.rudnikov.solarlab.service.RegistrationService;
import com.rudnikov.solarlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailService emailService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createNewAccount(SignupRequest request) {

        UserEntity userEntity = new UserEntity();
        ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity();

        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userEntity.setRole(UserRole.USER);
        userEntity.setIsAccountNonExpired(true);
        userEntity.setIsAccountNonLocked(true);
        userEntity.setIsCredentialsNonExpired(true);
        userEntity.setIsEnabled(false);

        confirmationTokenEntity.setUserEntity(userEntity);

        String token = confirmationTokenEntity.getToken();

        String confirmationLink = "http://localhost:8080/signup/confirm?token=" + token;

        userRepository.save(userEntity);
        confirmationTokenRepository.save(confirmationTokenEntity);

        emailService.sendComplexMail(
                request.getEmail(),
                "noreply@rudnikov.com",
                "Email address confirmation",
                confirmationLink
        );

        return token;
    }

    public Boolean confirmRegistration(String token) {

        ConfirmationTokenEntity confirmationTokenEntity = confirmationTokenService.fetchConfirmationToken(token);
        confirmationTokenService.confirmToken(confirmationTokenEntity);

        UserEntity userEntity = confirmationTokenEntity.getUserEntity();
        userEntity.setIsEnabled(true);
        userService.updateUser(userEntity.getId(), userEntity);

        confirmationTokenRepository.delete(confirmationTokenEntity);

        return true;
    }

}