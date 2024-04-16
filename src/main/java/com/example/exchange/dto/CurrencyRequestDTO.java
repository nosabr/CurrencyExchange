package com.example.exchange.dto;

public class CurrencyRequestDTO {
    private String code;
    private String fullname;
    private String sign;

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public CurrencyRequestDTO(String code, String fullname, String sign) {
        this.code = code;
        this.fullname = fullname;
        this.sign = sign;
    }

}
