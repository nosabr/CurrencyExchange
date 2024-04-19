package com.example.exchange.servlets;
import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.dao.ExchangeRatesDAO;
import com.example.exchange.services.ExchangeRatesService;
import com.example.exchange.util.ParameterValidator;
import com.example.exchange.util.RespondUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/exchange")

public class ExchangeServlet extends HttpServlet {
    RespondUtil respondUtil = new RespondUtil();
    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        if(!ParameterValidator.isCodeValid(from) || !ParameterValidator.isCodeValid(to)
                || !ParameterValidator.isRateValid(amount)){
            resp.setStatus(400);
            respondUtil.showError(resp,"Missing argument");
        } else {
            RequestExchangeRateDTO requestExchangeRateDTO = new RequestExchangeRateDTO();
            requestExchangeRateDTO.setBaseCurrencyCode(from);
            requestExchangeRateDTO.setTargetCurrencyCode(to);
            if (!exchangeRatesDAO.isPairInDB(requestExchangeRateDTO)){
                resp.setStatus(404);
                respondUtil.showError(resp, "No such exchange rate in DB");
            }
        }
    }
}
