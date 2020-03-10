package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Assurance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String numCareBulletin;
    private String date;
    private double amount;
    private String employerRegistrationNumber;
    private Etat status;

    public Assurance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCareBulletin() {
        return numCareBulletin;
    }

    public void setNumCareBulletin(String numBultinDeSoin) {
        this.numCareBulletin = numBultinDeSoin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double montant) {
        this.amount = montant;
    }

    public String getEmployerRegistrationNumber() {
        return employerRegistrationNumber;
    }

    public void setEmployerRegistrationNumber(String employerMatricule) {
        this.employerRegistrationNumber = employerMatricule;
    }

    public Etat getStatus() {
        return status;
    }

    public void setStatus(Etat etat) {
        this.status = etat;
    }

    public enum Etat{
        PAYED,NOT_PAYED
    }
}
