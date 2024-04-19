package com.example.exchange.DTO;

public class RequestExchangeDTO {
    private String from;
    private String to;
    private int amount;

    public RequestExchangeDTO(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }
}
