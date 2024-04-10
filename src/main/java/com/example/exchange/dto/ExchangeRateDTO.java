package com.example.exchange.dto;

public class ExchangeRateDTO {
    private int id;
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private double rate;

    public ExchangeRateDTO(int id, CurrencyDTO baseCurrencyDTO, CurrencyDTO targetCurrencyDTO, double rate) {
        this.id = id;
        this.baseCurrency = baseCurrencyDTO;
        this.targetCurrency = targetCurrencyDTO;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public CurrencyDTO getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyDTO getTargetCurrency() {
        return targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBaseCurrency(CurrencyDTO baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(CurrencyDTO targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
