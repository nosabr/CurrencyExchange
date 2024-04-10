package com.example.exchange.services;

import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.dto.CurrencyDTO;

import java.util.ArrayList;
import java.util.List;

public class CurrencyService {
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    public final List<CurrencyDTO> findAll(){
        List<CurrencyDTO> currencies = currenciesDAO.findAll();

        return currencies;
    }
}
