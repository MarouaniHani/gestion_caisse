package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Deplacement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String employerMatricule;
    private double montantDeDeplacement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployerMatricule() {
        return employerMatricule;
    }

    public void setEmployerMatricule(String employerMatricule) {
        this.employerMatricule = employerMatricule;
    }

    public double getMontantDeDeplacement() {
        return montantDeDeplacement;
    }

    public void setMontantDeDeplacement(double montantDeDeplacement) {
        this.montantDeDeplacement = montantDeDeplacement;
    }
}
