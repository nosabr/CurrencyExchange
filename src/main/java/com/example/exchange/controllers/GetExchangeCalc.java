package com.example.exchange.controllers;

import com.example.exchange.models.DBActions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/exchange")

public class GetExchangeCalc extends HttpServlet {
    DBActions actions = new DBActions();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actions.calculateExchangeRate(req, resp);
    }
}
