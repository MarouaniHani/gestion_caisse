package com.example.pfe.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BonPour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // numéro de dultin de soin 
    private String employerRegistrationNumber;
    //libellé
    private String wording;
    //montant
    private double amount;
    private boolean enInstance;
    private String date;

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

    public String getWording() {
        return wording;
    }

    public void setWording(String libelle) {
        this.wording = libelle;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double montant) {
        this.amount = montant;
    }

    public boolean isEnInstance() {
        return enInstance;
    }

    public void setEnInstance(boolean enInstance) {
        this.enInstance = enInstance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
