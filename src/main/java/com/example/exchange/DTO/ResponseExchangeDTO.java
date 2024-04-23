package com.example.exchange.DTO;

import com.example.exchange.entity.Currency;

public class ResponseExchangeDTO {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private double rate;
    private int amount;
    private double convertedAmount;

    public ResponseExchangeDTO(Currency baseCurrency, Currency targetCurrency, double rate,
                               int amount, double convertedAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public int getAmount() {
        return amount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
