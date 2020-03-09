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
    private String employerMatricule;
    private String libelle;
    private double montant;
    private boolean enInstance;
    private String date;

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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
