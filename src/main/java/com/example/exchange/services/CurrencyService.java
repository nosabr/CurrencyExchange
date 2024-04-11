package com.example.exchange.services;

import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.entity.Currency;

import java.util.List;

public class CurrencyService {
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    public final List<Currency> findAll(){
        return currenciesDAO.findAll();
    }
}
