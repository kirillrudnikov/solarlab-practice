package com.rudnikov.solarlab.service;

public interface EmailService {

    Boolean sendSimpleMail(String to, String from, String subject, String text);
    Boolean sendComplexMail(String to, String from, String subject, String text);

}