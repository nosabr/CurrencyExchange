package com.example.exchange.services;

import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.DTO.ResponseExchangeRateDTO;
import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.dao.ExchangeRatesDAO;
import com.example.exchange.entity.Currency;
import com.example.exchange.entity.ExchangeRate;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExchangeRatesService {
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAO();
    public List<ResponseExchangeRateDTO> findAll() {
        List<ExchangeRate> exchangeRates = exchangeRatesDAO.findAll();
        return exchangeRates.stream()
                .map(this::buildExchangeRateDto)
                .collect(toList());
    }

    public ResponseExchangeRateDTO findByCodes(RequestExchangeRateDTO request) {
        ExchangeRate exchangeRate = exchangeRatesDAO.findByCodes(request);
        return buildExchangeRateDto(exchangeRate);
    }

    public ResponseExchangeRateDTO add(RequestExchangeRateDTO request){
        exchangeRatesDAO.add(request);
        return findByCodes(request);
    }
    private ResponseExchangeRateDTO buildExchangeRateDto (ExchangeRate exchangeRate){
        if (exchangeRate == null){
            return null;
        } else {
            Currency baseCurrency = currenciesDAO.findById(exchangeRate.getBaseCurrencyId());
            Currency targetCurrency = currenciesDAO.findById(exchangeRate.getTargetCurrencyID());
            return new ResponseExchangeRateDTO(exchangeRate.getId(), baseCurrency,
                    targetCurrency, exchangeRate.getRate());
        }
    }

    public ResponseExchangeRateDTO update(RequestExchangeRateDTO requestExchangeRateDTO) {
        ExchangeRatesDAO.update(requestExchangeRateDTO);
        return findByCodes(requestExchangeRateDTO);
    }
}
