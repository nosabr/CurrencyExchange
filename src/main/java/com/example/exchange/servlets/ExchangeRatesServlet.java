package com.example.exchange.servlets;

import com.example.exchange.entity.ExchangeRate;
import com.example.exchange.services.ExchangeRatesService;
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
    RespondUtil respondUtil = new RespondUtil();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ExchangeRate> exchangeRateList = exchangeRatesService.findAll();
        if(exchangeRateList == null){
            resp.setStatus(404);
            respondUtil.showError(resp,"Empty DB");
        } else {
            respondUtil.showJSON(resp, exchangeRateList);
        }
    }
}
