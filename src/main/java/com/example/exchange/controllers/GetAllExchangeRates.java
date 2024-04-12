package com.example.exchange.controllers;

import com.example.exchange.entity.ExchangeRate;
import com.example.exchange.models.DBActions;
import com.example.exchange.services.ExchangeRatesService;
import com.example.exchange.util.RespondUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet ("/exchangeRates")
public class GetAllExchangeRates extends HttpServlet {

    DBActions actions = new DBActions();
    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    RespondUtil respondUtil = new RespondUtil();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //actions.getAllExchangeRates(resp);
        List<ExchangeRate> exchangeRateList = exchangeRatesService.findAll();
        if(exchangeRateList == null){
            resp.setStatus(404);
            respondUtil.showError(resp,"Empty DB");
        } else {
            respondUtil.showJSON(resp, exchangeRateList);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actions.insertNewExchangeRate(req,resp);
    }
}
