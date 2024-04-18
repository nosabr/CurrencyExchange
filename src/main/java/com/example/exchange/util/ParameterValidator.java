package com.example.exchange.util;

public class ParameterValidator {
    public static boolean isCodeValid(String code){
        return code != null && code.length() == 3;
    }
    public static boolean isSignValid(String sign){
        return !(sign == null || sign.length() != 1);
    }
    public static boolean isRateValid(String rate){
        return  rate != null && !rate.isEmpty();
    }

    public static boolean isNameValid(String name){
        return name != null;
    }

    public static boolean isPathValid(String path){
        return path != null;
    }
    public static boolean isPairValid(String pair){
        return !(pair == null || pair.length() != 6);
    }
}
