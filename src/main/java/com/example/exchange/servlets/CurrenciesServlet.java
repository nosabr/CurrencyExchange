package com.example.exchange.servlets;

import com.example.exchange.DTO.CurrencyRequestDTO;
import com.example.exchange.entity.Currency;
import com.example.exchange.services.CurrencyService;
import com.example.exchange.util.RespondUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

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
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        if(code == null || name == null || sign == null
            || code.isEmpty() || name.isEmpty() || sign.isEmpty()){
            resp.setStatus(400);
            respondUtil.showError(resp,"Required form is missing");
        } else if(code.length() != 3 || sign.length() != 1){
            resp.setStatus(400);
            respondUtil.showError(resp, "Invalid Request");
        } else if (service.findByCode(code) != null){
            resp.setStatus(409);
            respondUtil.showError(resp, "Given currency already in DB");
        }else {
            service.insert(new CurrencyRequestDTO(code,name,sign));
            respondUtil.showJSON(resp,service.findByCode(code));
        }
    }
}
