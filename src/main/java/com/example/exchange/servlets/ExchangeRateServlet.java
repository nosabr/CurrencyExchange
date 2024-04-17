package com.example.exchange.servlets;

import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.DTO.ResponseExchangeRateDTO;
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


@WebServlet ("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    RespondUtil respondUtil = new RespondUtil();
    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(!ParameterValidator.isPathValid(path) || !ParameterValidator.isPairValid(path.substring(1))){
            resp.setStatus(400);
            respondUtil.showError(resp, "Invalid URL");
        } else{
            path = path.substring(1);
            RequestExchangeRateDTO requestExchangeRateDTO = new RequestExchangeRateDTO();
            requestExchangeRateDTO.setBaseCurrencyCode(path.substring(0,3));
            requestExchangeRateDTO.setTargetCurrencyCode(path.substring(3,6));
            ResponseExchangeRateDTO responseExchangeRateDTO = exchangeRatesService.findByCodes(requestExchangeRateDTO);
            if(responseExchangeRateDTO == null){
                resp.setStatus(404);
                respondUtil.showError(resp, "DB is empty");
            } else {
                respondUtil.showJSON(resp, responseExchangeRateDTO);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
        } else {
            this.doPatch(req, resp);
        }
    }
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {

    }
}
