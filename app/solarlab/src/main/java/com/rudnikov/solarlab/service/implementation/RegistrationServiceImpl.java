package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.entity.ConfirmationToken;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.entity.enumerated.UserRole;
import com.rudnikov.solarlab.repository.ConfirmationTokenRepository;
import com.rudnikov.solarlab.repository.UserRepository;
import com.rudnikov.solarlab.request.SignupRequest;
import com.rudnikov.solarlab.service.ConfirmationTokenService;
import com.rudnikov.solarlab.service.EmailService;
import com.rudnikov.solarlab.service.RegistrationService;
import com.rudnikov.solarlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String createNewAccount(SignupRequest request) {

        User user = new User();
        ConfirmationToken confirmationToken = new ConfirmationToken();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setRole(UserRole.USER);

        confirmationToken.setUser(user);

        String token = confirmationToken.getToken();

        String link = "http://localhost:8080/api/registration/confirm?token=" + token;

        userRepository.save(user);
        confirmationTokenRepository.save(confirmationToken);

        emailService.sendComplexMail(
                request.getEmail(),
                "noreply@rudnikov.com",
                "Email address confirmation",
                link
        );

        return token;
    }

    public Boolean confirmRegistration(String token) {

        confirmationTokenService.confirmToken(confirmationTokenService.fetchConfirmationToken(token));

        return true;
    }

}