package com.example.pfe.dto;

import javax.validation.constraints.NotNull;

public class AssuranceDto {

    @NotNull
    private String numBultinDeSoin;
    private double montant;
    private String employerMatricule;

    public AssuranceDto(String numBultinDeSoin, double montant, String employerMatricule) {
        this.numBultinDeSoin = numBultinDeSoin;
        this.montant = montant;
        this.employerMatricule = employerMatricule;
    }

    public String getNumBultinDeSoin() {
        return numBultinDeSoin;
    }

    public void setNumBultinDeSoin(String numBultinDeSoin) {
        this.numBultinDeSoin = numBultinDeSoin;
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

}
