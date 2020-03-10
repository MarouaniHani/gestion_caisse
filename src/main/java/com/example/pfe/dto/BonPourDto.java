package com.example.pfe.dto;

public class BonPourDto {
    private String employerRegistrationNumber;
    private String wording;
    private double amount;

    public String getEmployerRegistrationNumber() {
        return employerRegistrationNumber;
    }

    public void setEmployerRegistrationNumber(String employerRegistrationNumber) {
        this.employerRegistrationNumber = employerRegistrationNumber;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
