package com.example.pfe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TravelDto {
    private String employerRegistrationNumber;
    @NotNull
    private double amountOfTravel;


    public TravelDto(String employerRegistrationNumber, @NotBlank @NotNull double amountOfTravel) {
        this.employerRegistrationNumber = employerRegistrationNumber;
        this.amountOfTravel = amountOfTravel;
    }

    public String getEmployerRegistrationNumber() {
        return employerRegistrationNumber;
    }

    public void setEmployerRegistrationNumber(String employerRegistrationNumber) {
        this.employerRegistrationNumber = employerRegistrationNumber;
    }

    public double getAmountOfTravel() {
        return amountOfTravel;
    }

    public void setAmountOfTravel(double amountOfTravel) {
        this.amountOfTravel = amountOfTravel;
    }
}
