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
    private String numBultinDeSoin;
    private String date;
    private double montant;
    private String employerMatricule;
    private Etat etat;

    public Assurance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumBultinDeSoin() {
        return numBultinDeSoin;
    }

    public void setNumBultinDeSoin(String numBultinDeSoin) {
        this.numBultinDeSoin = numBultinDeSoin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getEmployerMatricule() {
        return employerMatricule;
    }

    public void setEmployerMatricule(String employerMatricule) {
        this.employerMatricule = employerMatricule;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public enum Etat{
        PAYED,NOT_PAYED
    }
}
