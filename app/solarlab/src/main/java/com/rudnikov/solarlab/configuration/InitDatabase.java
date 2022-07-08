package com.rudnikov.solarlab.configuration;

import com.rudnikov.solarlab.entity.Advert;
import com.rudnikov.solarlab.entity.Comment;
import com.rudnikov.solarlab.entity.User;
import com.rudnikov.solarlab.entity.enumerated.UserRole;
import com.rudnikov.solarlab.repository.AdvertRepository;
import com.rudnikov.solarlab.repository.CommentRepository;
import com.rudnikov.solarlab.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Slf4j
@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(UserRepository userRepository, AdvertRepository advertRepository, CommentRepository commentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        User firstUser = new User("kirill.rudnikov", "kirillrudnikov811@gmail.com", "+79780371649", bCryptPasswordEncoder.encode("dasha123"), UserRole.ADMINISTRATOR, true, true, true, true);
        User secondUser = new User("nastya.boot", "asya.1997@yandex.ru", "+79781697410", bCryptPasswordEncoder.encode("nastya4.f[D"), UserRole.USER, true, true, true, true);

        Advert firstAdvert = new Advert(firstUser,"BMW X5", "Продаю новенькую бмвшку", "Автомобили", "Севастополь");
        Advert secondAdvert = new Advert(secondUser,"AMD FX-8320, 16GB DDR3-2133 MHz, nVIDIA GeForce RTX 2070Ti 8GB GDDR6", "Продаю старый компьютер", "Компьютеры", "Керчь");

        Comment firstComment = new Comment(firstUser, "Первый комментарий", "Бла-бла-бла.", firstAdvert);
        Comment secondComment = new Comment(secondUser, "Второй комментарий", "Привет! Очень интересное предложение, как с Вами связаться?", firstAdvert);

        Comment thirdComment = new Comment(firstUser, "Третий комментарий","Продавец очень хороший, рекомедую!", secondAdvert);
        Comment fourthComment = new Comment(secondUser, "Четвёртый комментарий","Привет! Очень интересное предложение, как с Вами связаться?", secondAdvert);

        return args -> {
            log.warn("Preloading data...");
            userRepository.save(firstUser);
            userRepository.save(secondUser);
            advertRepository.save(firstAdvert);
            advertRepository.save(secondAdvert);
            commentRepository.save(firstComment);
            commentRepository.save(secondComment);
            commentRepository.save(thirdComment);
            commentRepository.save(fourthComment);
        };
    }

}