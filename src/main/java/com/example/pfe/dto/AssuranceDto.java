package com.example.pfe.dto;

import javax.validation.constraints.NotNull;

public class AssuranceDto {

    @NotNull
    private String numCareBulletin;
    private double amount;
    private String employerRegistrationNumber;

    public AssuranceDto(String numCareBulletin, double amount, String employerRegistrationNumber) {
        this.numCareBulletin = numCareBulletin;
        this.amount = amount;
        this.employerRegistrationNumber = employerRegistrationNumber;
    }

    public String getNumCareBulletin() {
        return numCareBulletin;
    }

    public void setNumCareBulletin(String numCareBulletin) {
        this.numCareBulletin = numCareBulletin;
    }

        public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmployerRegistrationNumber() {
        return employerRegistrationNumber;
    }

    public void setEmployerRegistrationNumber(String employerRegistrationNumber) {
        this.employerRegistrationNumber = employerRegistrationNumber;
    }

}
