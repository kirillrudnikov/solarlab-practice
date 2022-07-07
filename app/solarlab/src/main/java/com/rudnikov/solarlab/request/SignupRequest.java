package com.rudnikov.solarlab.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String captcha;

}