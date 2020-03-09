package com.example.pfe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmployerDto {
    @NotNull
    @NotBlank
    private String nom;
    @NotNull
    @NotBlank
    private String prenom;
    private String position;
    @NotNull
    @NotBlank
    private String motDePass;
    @NotNull
    @NotBlank
    private String service;

    public EmployerDto(String nom, String prenom, String position, String motDePass, String service) {
        this.nom = nom;
        this.prenom = prenom;
        this.position = position;
        this.motDePass = motDePass;
        this.service = service;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMotDePass() {
        return motDePass;
    }

    public void setMotDePass(String motDePass) {
        this.motDePass = motDePass;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
