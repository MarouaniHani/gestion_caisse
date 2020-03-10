package com.example.pfe.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    private int registrationNumber;
    @NotNull
    private String password;

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
