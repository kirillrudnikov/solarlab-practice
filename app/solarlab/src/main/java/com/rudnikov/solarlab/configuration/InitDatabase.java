package com.rudnikov.solarlab.configuration;

import com.rudnikov.solarlab.entity.AdvertEntity;
import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.entity.UserRole;
import com.rudnikov.solarlab.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           AdvertRepository advertRepository,
                           CommentRepository commentRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder
    ) {

        UserEntity firstUserEntity = UserEntity.builder()
                .username("kirill.rudnikov")
                .email("kirillrudnikov811@gmail.com")
                .phoneNumber("+79780071234")
                .password(bCryptPasswordEncoder.encode("123456"))
                .role(UserRole.ADMINISTRATOR)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        UserEntity secondUserEntity = UserEntity.builder()
                .username("nastya.boot")
                .email("asya.1997@yandex.ru")
                .phoneNumber("+79780009100")
                .password(bCryptPasswordEncoder.encode("123456a"))
                .role(UserRole.USER)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        AdvertEntity firstAdvertEntity = AdvertEntity.builder()
                .author(firstUserEntity)
                .title("AMD Ryzen 5600X3D 4.5 GHz")
                .content("Продаю новый процессор. Дата покупки: 05.12.2022. Гарантия 2 года.")
                .region("Севастополь")
                .category("Компьютеры")
                .subCategory("Аппаратное обеспечение")
                .build();

        AdvertEntity secondAdvertEntity = AdvertEntity.builder()
                .author(secondUserEntity)
                .title("BMW X5")
                .content("Продаю новую машину")
                .region("Москва")
                .category("Транспорт")
                .build();

        CommentEntity firstComment = CommentEntity.builder()
                .author(firstUserEntity)
                .title("Комментарий от kirill.rudnikov")
                .content("Чё то напечатал...")
                .advert(firstAdvertEntity)
                .build();

        CommentEntity secondComment = CommentEntity.builder()
                .author(firstUserEntity)
                .title("Комментарий от nastya.boot")
                .content("Чё то напечатала...")
                .advert(secondAdvertEntity)
                .build();

        return args -> {
            log.warn("Preloading data...");
            userRepository.saveAll(List.of(firstUserEntity, secondUserEntity));
            advertRepository.saveAll(List.of(firstAdvertEntity, secondAdvertEntity));
            commentRepository.saveAll(List.of(firstComment, secondComment));
        };
    }

}