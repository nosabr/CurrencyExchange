package com.example.exchange.services;

import com.example.exchange.DTO.CurrencyRequestDTO;
import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.entity.Currency;

import java.util.List;

public class CurrencyService {
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    public List<Currency> findAll(){
        return currenciesDAO.findAll();
    }

    public Currency findByCode(String code){
        return currenciesDAO.findByCode(code.toUpperCase());
    }

    public void insert(CurrencyRequestDTO currencyRequestDTO) {
        currenciesDAO.insert(currencyRequestDTO);
    }
}
