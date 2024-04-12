package com.example.exchange.services;

import com.example.exchange.dao.ExchangeRatesDAO;
import com.example.exchange.entity.ExchangeRate;

import java.util.List;

public class ExchangeRatesService {
    ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAO();
    public List<ExchangeRate> findAll() {
        return exchangeRatesDAO.findAll();
    }
}
