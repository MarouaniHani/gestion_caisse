package com.example.pfe.dto;

import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    private int matricule;
    @NotNull
    private String motDePass;

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getMotDePass() {
        return motDePass;
    }

    public void setMotDePass(String motDePass) {
        this.motDePass = motDePass;
    }
}
