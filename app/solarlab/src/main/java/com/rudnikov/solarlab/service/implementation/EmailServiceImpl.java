package com.rudnikov.solarlab.service.implementation;

import com.rudnikov.solarlab.request.SignupRequest;
import com.rudnikov.solarlab.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public Boolean sendSimpleMail(String to, String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        return true;
    }

    public Boolean sendComplexMail(String to, String from, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(message);

            log.info("Mail sent to: {}, with subject: {}", to, subject);

            return true;
        } catch (MessagingException exception) {
            log.error(exception.getMessage());
            return false;
        }
    }

}