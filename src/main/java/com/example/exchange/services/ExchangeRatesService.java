package com.example.exchange.services;

import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.DTO.ResponseExchangeDTO;
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

    public ResponseExchangeDTO calculate(RequestExchangeRateDTO directRequest) {
        int amount = Integer.parseInt(directRequest.getRate());
        Currency baseCurrency = currenciesDAO.findByCode(directRequest.getBaseCurrencyCode());
        Currency targetCurrency = currenciesDAO.findByCode(directRequest.getTargetCurrencyCode());
        RequestExchangeRateDTO reverseRequest = new RequestExchangeRateDTO(directRequest.getTargetCurrencyCode(),
                directRequest.getBaseCurrencyCode());
        ExchangeRate directExchange = exchangeRatesDAO.findByCodes(directRequest);
        ExchangeRate reverseExchange = exchangeRatesDAO.findByCodes(reverseRequest);
        if(directExchange != null){
            return new ResponseExchangeDTO(baseCurrency,targetCurrency,directExchange.getRate(),
                    amount, amount* directExchange.getRate());
        } else if(reverseExchange != null) {
            return new ResponseExchangeDTO(baseCurrency,targetCurrency,reverseExchange.getRate(),
                    amount, amount / reverseExchange.getRate());
        } else {
            ExchangeRate baseToUsd = exchangeRatesDAO.findByCodes(new RequestExchangeRateDTO(
                    "USD", directRequest.getBaseCurrencyCode()));
            ExchangeRate usdToTarget = exchangeRatesDAO.findByCodes(new RequestExchangeRateDTO(
                    "USD",directRequest.getTargetCurrencyCode()));
            if(baseToUsd != null && usdToTarget != null){
                double rate = 1 / baseToUsd.getRate() * usdToTarget.getRate();
                return new ResponseExchangeDTO(baseCurrency, targetCurrency, rate,
                        amount, amount * rate);
            } else {
                return null;
            }
        }

    }
}
