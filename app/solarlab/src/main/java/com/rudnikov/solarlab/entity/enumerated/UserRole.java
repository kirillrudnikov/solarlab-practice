package com.rudnikov.solarlab.entity.enumerated;

public enum UserRole {

    USER("Пользователь"),
    ADMINISTRATOR("Администратор");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}