package com.example.exchange.servlets;

import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.DTO.ResponseExchangeRateDTO;
import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.dao.ExchangeRatesDAO;
import com.example.exchange.entity.ExchangeRate;
import com.example.exchange.services.ExchangeRatesService;
import com.example.exchange.util.ParameterValidator;
import com.example.exchange.util.RespondUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet ("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAO();
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    RespondUtil respondUtil = new RespondUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ResponseExchangeRateDTO> exchangeRateList = exchangeRatesService.findAll();
        if(exchangeRateList == null){
            resp.setStatus(404);
            respondUtil.showError(resp,"Empty DB");
        } else {
            respondUtil.showJSON(resp, exchangeRateList);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        if(ParameterValidator.isCodeValid(baseCurrencyCode) && ParameterValidator.isCodeValid(targetCurrencyCode)
                && ParameterValidator.isRateValid(rate)){
            RequestExchangeRateDTO requestExchangeRateDTO = new RequestExchangeRateDTO();
            requestExchangeRateDTO.setBaseCurrencyCode(baseCurrencyCode.toUpperCase());
            requestExchangeRateDTO.setTargetCurrencyCode(targetCurrencyCode.toUpperCase());
            requestExchangeRateDTO.setRate(rate);
            if(exchangeRatesDAO.isPairInDB(requestExchangeRateDTO)){
                resp.setStatus(409);
                respondUtil.showError(resp, "That pair already in DB");
            } else if(currenciesDAO.findByCode(baseCurrencyCode) == null
                        || currenciesDAO.findByCode(targetCurrencyCode) == null){
                resp.setStatus(404);
                respondUtil.showError(resp, "No such currencies in DB");
            }else {
                ResponseExchangeRateDTO responseExchangeRateDTO =  exchangeRatesService.add(requestExchangeRateDTO);
                respondUtil.showJSON(resp, responseExchangeRateDTO);
            }

        } else {
            resp.setStatus(400);
            respondUtil.showError(resp, "Wrong request");
        }
    }
}
