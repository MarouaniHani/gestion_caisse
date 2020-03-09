package com.example.pfe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeplacementDto {
    private String employerMatricule;
    @NotNull
    private double montantDeDeplacement;


    public DeplacementDto(String employerMatricule, @NotBlank @NotNull double montantDeDeplacement) {
        this.employerMatricule = employerMatricule;
        this.montantDeDeplacement = montantDeDeplacement;
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
