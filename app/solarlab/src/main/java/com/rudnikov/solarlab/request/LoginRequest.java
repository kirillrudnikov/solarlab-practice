package com.rudnikov.solarlab.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String login; // Username or E-mail address
    private String password;

}