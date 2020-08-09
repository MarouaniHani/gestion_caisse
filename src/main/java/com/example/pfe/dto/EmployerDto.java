package com.example.pfe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmployerDto {
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private String position;
    private String role;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String service;

    public EmployerDto(String firstName, String lastName, String position, String role, String password, String service) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.role = role;
        this.password = password;
        this.service = service;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
