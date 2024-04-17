package com.example.exchange.entity;

public class ExchangeRate {
    private int id;
    private int baseCurrencyId;
    private int targetCurrencyID;
    private double rate;

    public ExchangeRate(int id, int baseCurrencyId, int targetCurrencyID, double rate) {
        this.id = id;
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyID = targetCurrencyID;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public int getTargetCurrencyID() {
        return targetCurrencyID;
    }

    public double getRate() {
        return rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBaseCurrencyId(int baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public void setTargetCurrencyID(int targetCurrencyID) {
        this.targetCurrencyID = targetCurrencyID;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}