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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
            requestExchangeRateDTO.setBaseCurrencyCode(path.substring(0,3).toUpperCase());
            requestExchangeRateDTO.setTargetCurrencyCode(path.substring(3,6).toUpperCase());
            ResponseExchangeRateDTO responseExchangeRateDTO = exchangeRatesService.findByCodes(requestExchangeRateDTO);
            if(responseExchangeRateDTO == null){
                resp.setStatus(404);
                respondUtil.showError(resp, "No such exchange pair");
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
        String path = req.getPathInfo();
        String rate = getParameter(req);
        if(!ParameterValidator.isPathValid(path) || !ParameterValidator.isPairValid(path.substring(1))
                || !ParameterValidator.isRateValid(rate)){
            resp.setStatus(400);
            respondUtil.showError(resp, "Invalid Request");
        } else{
            path = path.substring(1);
            RequestExchangeRateDTO requestExchangeRateDTO = new RequestExchangeRateDTO();
            requestExchangeRateDTO.setBaseCurrencyCode(path.substring(0,3).toUpperCase());
            requestExchangeRateDTO.setTargetCurrencyCode(path.substring(3,6).toUpperCase());
            requestExchangeRateDTO.setRate(rate);
            ResponseExchangeRateDTO responseExchangeRateDTO = exchangeRatesService.findByCodes(requestExchangeRateDTO);
            if(responseExchangeRateDTO == null){
                resp.setStatus(404);
                respondUtil.showError(resp, "No such exchange pair");
            } else {
                responseExchangeRateDTO = exchangeRatesService.update(requestExchangeRateDTO);
                respondUtil.showJSON(resp, responseExchangeRateDTO);
            }
        }
    }
    public static String getParameter(HttpServletRequest request) {
        BufferedReader br;
        String[] par;

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(
                    request.getInputStream());
            br = new BufferedReader(reader);
            String data = br.readLine();
            par = data.split("=");
            if (par.length == 1) {
                return "";
            } else {
                return par[1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
