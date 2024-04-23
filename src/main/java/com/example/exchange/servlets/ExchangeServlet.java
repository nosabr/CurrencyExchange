package com.example.exchange.servlets;
import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.DTO.ResponseExchangeDTO;
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
import java.util.Optional;

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
            RequestExchangeRateDTO requestExchangeRateDTO = new RequestExchangeRateDTO(
                    from.toUpperCase(),to.toUpperCase());
            requestExchangeRateDTO.setRate(amount);
            ResponseExchangeDTO responseExchangeDTO = exchangeRatesService.calculate(requestExchangeRateDTO);
            if(responseExchangeDTO == null){
                resp.setStatus(404);
                respondUtil.showError(resp,"Can not process that exchange");
            } else {
                respondUtil.showJSON(resp,responseExchangeDTO);
            }
        }
    }
}
