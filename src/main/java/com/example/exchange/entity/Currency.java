package com.example.exchange.entity;
public class Currency {
    private int id;
    private String code;
    private String fullname;
    private String sign;

    public Currency(int id, String code, String fullname, String sign) {
        this.id = id;
        this.code = code;
        this.fullname = fullname;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullname() {
        return fullname;
    }

    public String getSign() {
        return sign;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
