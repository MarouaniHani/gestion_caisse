package com.example.pfe.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PaymentDemand {

    @Id
    private int id;
    private String bonPourId;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBonPourId() {
        return bonPourId;
    }

    public void setBonPourId(String bonPourId) {
        this.bonPourId = bonPourId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

