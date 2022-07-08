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

        User user = new User();
        ConfirmationToken confirmationToken = new ConfirmationToken();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);

        confirmationToken.setUser(user);

        String token = confirmationToken.getToken();

        String confirmationLink = "http://localhost:8080/signup/confirm?token=" + token;

        userRepository.save(user);
        confirmationTokenRepository.save(confirmationToken);

        emailService.sendComplexMail(
                request.getEmail(),
                "noreply@rudnikov.com",
                "Email address confirmation",
                confirmationLink
        );

        return token;
    }

    public Boolean confirmRegistration(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService.fetchConfirmationToken(token);
        confirmationTokenService.confirmToken(confirmationToken);

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userService.updateUser(user.getId(), user);

        return true;
    }

}