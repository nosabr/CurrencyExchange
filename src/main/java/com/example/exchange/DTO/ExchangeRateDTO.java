package com.example.exchange.DTO;

public class ExchangeRateDTO {
    private int id;
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private int rate;

    public ExchangeRateDTO(int id, CurrencyDTO baseCurrencyDTO, CurrencyDTO targetCurrencyDTO, int rate) {
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

    public int getRate() {
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
