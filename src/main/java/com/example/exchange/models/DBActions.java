package com.example.exchange.models;



import com.example.exchange.DTO.CurrencyDTO;
import com.example.exchange.DTO.ExchangeRateDTO;
import com.example.exchange.DTO.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DBActions {
    private Statement statement;
    private PrintWriter out;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void getAllCurrencies(HttpServletResponse resp){
        DBConnection connection = new DBConnection(); // подключение к БД
        String query = "SELECT * FROM currencies"; // запрос
        try {
            statement = connection.getConnection().createStatement(); //создаем утверждение?
            ResultSet resultSet = statement.executeQuery(query);
            List<CurrencyDTO> currencyDTOList = new ArrayList<>(); // список со всеми рядами данных
            while (resultSet.next()){ // добавление всех полей в список
                currencyDTOList.add(new CurrencyDTO(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
            resp.setContentType("application/json; charset=UTF-8");
            out = resp.getWriter();
            String out1 = objectMapper.writeValueAsString(currencyDTOList);
            out.println(out1);
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException | IOException e){
            resp.setStatus(500);
            showError(resp, "DB is not available");
        }
    }

    public void getAllExchangeRates(HttpServletResponse resp){
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM exchangerates";
        try {
            statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<ExchangeRateDTO> exchangeRateDTOlist = new ArrayList<>();
            while(resultSet.next()){
                exchangeRateDTOlist.add(new ExchangeRateDTO(resultSet.getInt(1),
                                getCurrencyDTObyID(resultSet.getInt(2)),
                                getCurrencyDTObyID(resultSet.getInt(3)),
                                resultSet.getInt(4)));
            }
            resp.setContentType("application/json; charset=UTF-8");
            out = resp.getWriter();
            String out1 = objectMapper.writeValueAsString(exchangeRateDTOlist);
            out.println(out1);
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException | IOException e) {
            resp.setStatus(500);
            showError(resp, String.valueOf(e));
        }
    }

    private CurrencyDTO getCurrencyDTObyID(int id){
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM currencies WHERE id = " + id + ";";
        CurrencyDTO out;
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            out = new CurrencyDTO(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    public void showError(HttpServletResponse resp, String message){
        try {
            resp.setContentType("application/json; charset=UTF-8");
            out = resp.getWriter();
            out.println(objectMapper.writeValueAsString(new MessageDTO(message)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSpecificCurrency(HttpServletRequest req, HttpServletResponse resp) {
        DBConnection connection = new DBConnection();
        String path = req.getRequestURI();
        String[] urls = path.split("/");
        if(false){
            resp.setStatus(400);
            showError(resp, "Wrong url");
        } else {
            try {
                String query = "SELECT * FROM currencies WHERE code = '" + urls[urls.length - 1] + "'";
                Statement statement = connection.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                String out1 = null;
                if(resultSet.next()){
                    CurrencyDTO targetCurrency = new CurrencyDTO(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4));
                    resp.setContentType("application/json; charset=UTF-8");
                    out = resp.getWriter();
                    out1 = objectMapper.writeValueAsString(targetCurrency);
                    out.println(out1);
                }
                if(out1 == null){
                    resp.setStatus(404);
                    showError(resp, "No such currency");
                }
                statement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException | IOException e) {
                resp.setStatus(500);
                showError(resp, "DB error");
            }
        }
    }
}
