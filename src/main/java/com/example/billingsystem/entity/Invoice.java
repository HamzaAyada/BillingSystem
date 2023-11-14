package com.example.billingsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Invoice {

    @JsonProperty("clientId")
    private Long clientId;
    @JsonProperty("clientReference")
    private String clientReference;
    @JsonProperty("clientType")
    private String clientType;
    @JsonProperty("energyType")
    private String energyType;
    @JsonProperty("consumedKWh")
    private double consumedKWh;
    @JsonProperty("unitPrice")
    private double unitPrice;
    @JsonProperty("totalAmount")
    private double totalAmount;


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public double getConsumedKWh() {
        return consumedKWh;
    }

    public void setConsumedKWh(double consumedKWh) {
        this.consumedKWh = consumedKWh;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

