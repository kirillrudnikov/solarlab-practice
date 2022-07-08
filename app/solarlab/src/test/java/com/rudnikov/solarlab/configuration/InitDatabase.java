package com.rudnikov.solarlab.configuration;

import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.entity.enumerated.UserRole;
import com.rudnikov.solarlab.repository.AdvertRepository;
import com.rudnikov.solarlab.repository.CommentRepository;
import com.rudnikov.solarlab.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
public class InitDatabase {

    @Bean
    CommandLineRunner init(UserRepository userRepository, AdvertRepository advertRepository, CommentRepository commentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        return args -> {
            User firstUser = User.builder()
                    .username("kirill.rudnikov")
                    .email("kirillrudnikov811@gmail.com")
                    .phoneNumber("+79780371649")
                    .password(bCryptPasswordEncoder.encode("kirill123"))
                    .role(UserRole.ADMINISTRATOR)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .build();

            User secondUser = User.builder()
                    .username("alexander96")
                    .email("alex.gomes@yahoo.com")
                    .phoneNumber("+12345678910")
                    .password(bCryptPasswordEncoder.encode("sasha123"))
                    .role(UserRole.USER)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(false)
                    .build();

            User thirdUser = User.builder()
                    .username("ekaterina.boot")
                    .email("ekatze.boot@yandex.ru")
                    .phoneNumber("+81940124890")
                    .password(bCryptPasswordEncoder.encode("katya123"))
                    .role(UserRole.USER)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(false)
                    .isAccountNonLocked(false)
                    .build();

            User fourthUser = User.builder()
                    .username("evgeniya.2000")
                    .email("evgeniya.2000year@mail.ru")
                    .phoneNumber("+79781677400")
                    .password(bCryptPasswordEncoder.encode("jenya123"))
                    .role(UserRole.USER)
                    .isEnabled(true)
                    .isAccountNonExpired(false)
                    .isCredentialsNonExpired(false)
                    .isAccountNonLocked(false)
                    .build();

            userRepository.save(firstUser);
            userRepository.save(secondUser);
            userRepository.save(thirdUser);
            userRepository.save(fourthUser);
        };

    }

}