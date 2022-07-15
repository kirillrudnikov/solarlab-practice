package com.rudnikov.solarlab.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ANONYMOUS("ANONYMOUS"),
    USER("USER"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }
}