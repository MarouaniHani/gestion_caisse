package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String employerRegistrationNumber;
    private double travelAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployerRegistrationNumber() {
        return employerRegistrationNumber;
    }

    public void setEmployerRegistrationNumber(String employerMatricule) {
        this.employerRegistrationNumber = employerMatricule;
    }

    public double getTravelAmount() {
        return travelAmount;
    }

    public void setTravelAmount(double montantDeDeplacement) {
        this.travelAmount = montantDeDeplacement;
    }
}
