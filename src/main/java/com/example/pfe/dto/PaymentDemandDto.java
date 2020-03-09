package com.example.pfe.dto;

import javax.validation.constraints.NotNull;

public class PaymentDemandDto {
    @NotNull
    private String bonPourId;
    private String date;

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
