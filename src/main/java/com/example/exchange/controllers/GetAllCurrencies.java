package com.example.exchange.controllers;

import com.example.exchange.dao.CurrenciesDAO;
import com.example.exchange.dto.CurrencyDTO;
import com.example.exchange.models.DBActions;
import com.example.exchange.services.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/currencies")
public class GetAllCurrencies extends HttpServlet {

    DBActions actions = new DBActions();
    private PrintWriter out;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CurrencyService service = new CurrencyService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CurrencyDTO> currencies = service.findAll();
        resp.setContentType("application/json; charset=UTF-8");
        out = resp.getWriter();
        out.println(objectMapper.writeValueAsString(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actions.insertNewCurrency(req,resp);
    }
}
