package com.example.exchange.dao;

import com.example.exchange.dto.CurrencyDTO;
import com.example.exchange.models.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO {
    private static final String FIND_ALL = "SELECT * FROM currencies;";

    public List<CurrencyDTO> findAll() {
        List<CurrencyDTO> currency = new ArrayList<>();
        try(){

        } catch (){

        }
        return currency;
    }
}
