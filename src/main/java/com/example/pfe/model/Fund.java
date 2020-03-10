package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double sold;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double solde) {
        this.sold = solde;
    }
}
