package com.rudnikov.solarlab.dto;

import lombok.Data;

@Data
public class UserSavingDto {

    private String username;
    private String email;
    private String password;
    private String phoneNumber;

}