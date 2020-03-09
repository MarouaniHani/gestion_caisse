package com.example.pfe.dto;

public class BonPourDto {
    private String employerMatricule;
    private String libelle;
    private double montant;

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
}
