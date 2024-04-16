package com.example.exchange.servlets;

import com.example.exchange.models.DBActions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //actions.getSpecificExchangeRate(req,resp);
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
        //actions.updateExchangeRate(req,resp);
    }
}
