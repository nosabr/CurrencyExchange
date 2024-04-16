package com.example.exchange.util;

public class ParameterValidator {
    public static boolean checkCode(String code){
        return !(code == null || code.length() != 3); //true если правильно
    }
    public static boolean checkSign(String sign){
        return !(sign == null || sign.length() != 1);
    }
    public static boolean checkRate(String rate){
        return  rate != null;
    }

    public static boolean checkName(String name){
        return name != null;
    }

}
