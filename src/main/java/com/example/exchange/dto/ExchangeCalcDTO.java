package com.example.exchange.dto;

public class ExchangeCalcDTO {
    private CurrencyDTO from;
    private CurrencyDTO to;
    private double rate;
    private double amount;
    private double convertedAmount;

    public void setFrom(CurrencyDTO from) {
        this.from = from;
    }

    public ExchangeCalcDTO(CurrencyDTO from, CurrencyDTO to, double rate, double amount, double convertedAmount) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public void setTo(CurrencyDTO to) {
        this.to = to;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public CurrencyDTO getFrom() {
        return from;
    }

    public CurrencyDTO getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

    public double getAmount() {
        return amount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }
}
