package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int registrationNumber;
    private String firstName;
    private String lastName;
    private Postion postion;
    private Role role;
    private String password;
    private String service;
    //plafond
    private double ceilingAssurance;


    public Employer() {
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Postion getPostion() {
        return postion;
    }

    public void setPostion(Postion postion) {
        this.postion = postion;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String motDePass) {
        this.password = motDePass;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getCeilingAssurance() {
        return ceilingAssurance;
    }

    public void setCeilingAssurance(double ceilingAssurance) {
        this.ceilingAssurance = ceilingAssurance;
    }

    public enum Postion {
        CADRE, MAITRISE, EXECUTEUR
    }

    public enum Role {
        ADMIN, CAISSIER, AGENT_SERVICE_SOCIAL, AGENT_PARC_AUTO
    }
}
