package com.example.exchange.dto;

import com.example.exchange.entity.Currency;

public class ExchangeCalcDTO {
    private Currency from;
    private Currency to;
    private double rate;
    private double amount;
    private double convertedAmount;

    public void setFrom(Currency from) {
        this.from = from;
    }

    public ExchangeCalcDTO(Currency from, Currency to, double rate, double amount, double convertedAmount) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public void setTo(Currency to) {
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

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
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
