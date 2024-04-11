package com.example.exchange.controllers;

import com.example.exchange.entity.Currency;
import com.example.exchange.models.DBActions;
import com.example.exchange.services.CurrencyService;
import com.example.exchange.util.RespondUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class GetAllCurrencies extends HttpServlet {

    RespondUtil respondUtil = new RespondUtil();
    CurrencyService service = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = service.findAll();
        if(currencies.isEmpty()){
            resp.setStatus(404);
            respondUtil.showError(resp, "Database is empty");
        } else {
            respondUtil.showJSON(resp,currencies);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //actions.insertNewCurrency(req,resp);

    }
}
