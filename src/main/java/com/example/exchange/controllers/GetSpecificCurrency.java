package com.example.exchange.controllers;

import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.entity.Currency;
import com.example.exchange.models.DBActions;
import com.example.exchange.services.CurrencyService;
import com.example.exchange.util.RespondUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet ("/currency/*")
public class GetSpecificCurrency extends HttpServlet {
    CurrencyService currencyService = new CurrencyService();
    RespondUtil respondUtil = new RespondUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String[] urls = path.split("/");
        if(urls[urls.length - 1].length() != 3 || !urls[urls.length - 2].equals("currency")){
            resp.setStatus(400);
            respondUtil.showError(resp, "Wrong url");
        } else {
            String code = urls[urls.length - 1];
            Currency currency = currencyService.findByCode(code);

        }
    }


}
