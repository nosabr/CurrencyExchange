package com.example.exchange.models;



import com.example.exchange.DTO.CurrencyDTO;
import com.example.exchange.DTO.ExchangeRateDTO;
import com.example.exchange.DTO.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.jdbc.ResourceLock;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DBActions {
    private PreparedStatement prStatement;
    private PrintWriter out;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void getAllCurrencies(HttpServletResponse resp){
        DBConnection connection = new DBConnection(); // подключение к БД
        String query = "SELECT * FROM currencies"; // запрос
        try {
            prStatement = connection.getConnection().prepareStatement(query); //создаем утверждение?
            ResultSet resultSet = prStatement.executeQuery();
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
            prStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException | IOException e){
            resp.setStatus(500);
            showError(resp, "DB is not available");
        }
    }

    public void getSpecificCurrency(HttpServletRequest req, HttpServletResponse resp) {
        DBConnection connection = new DBConnection();
        String path = req.getRequestURI();
        String[] urls = path.split("/");
        if(urls[urls.length - 1].length() != 3 || !urls[urls.length - 2].equals("currency")){
            resp.setStatus(400);
            showError(resp, "Wrong url");
        } else {
            try {
                String query = "SELECT * FROM currencies WHERE code = ?";
                prStatement = connection.getConnection().prepareStatement(query);
                prStatement.setString(1, urls[urls.length - 1].toUpperCase());
                ResultSet resultSet = prStatement.executeQuery();
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
                prStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException | IOException e) {
                resp.setStatus(500);
                showError(resp, "DB error");
            }
        }
    }

    public void insertNewCurrency(HttpServletRequest req, HttpServletResponse resp) {
        DBConnection connection = new DBConnection();
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        if(name == null || code == null || sign == null ||
                name.isEmpty() || code.isEmpty() || sign.isEmpty()){
                    resp.setStatus(400);
                    showError(resp, "A required form is missing");
        }else if(code.length() != 3){
            resp.setStatus(400);
            showError(resp,"Code's length must be 3 symbols");
        } else if(findCurrencyByCode(code) != 0){
            resp.setStatus(409);
            showError(resp, "Currency already exists");
        } else {
            String queryInsert = "INSERT INTO currencies (fullname, code, sign) VALUES (?,?,?)";
            String querySelect =  "SELECT * FROM currencies WHERE code = ?";
            try {
                //Вызываем Insert
                prStatement = connection.getConnection().prepareStatement(queryInsert);
                prStatement.setString(1,name);
                prStatement.setString(2,code);
                prStatement.setString(3,sign);
                prStatement.executeUpdate();
                // Вызываем Select
                prStatement = connection.getConnection().prepareStatement(querySelect);
                prStatement.setString(1,code);
                ResultSet rs = prStatement.executeQuery();
                if (rs.next()) {
                    resp.setContentType("application/json; charset=UTF-8");
                    out = resp.getWriter();
                    String out1 = objectMapper.writeValueAsString(new CurrencyDTO(rs.getInt(1),
                            rs.getString(2), rs.getString(3), rs.getString(4)));
                    out.println(out1);
                }
                prStatement.close();
                rs.close();
                connection.close();
            } catch (SQLException | IOException e) {
                resp.setStatus(500);
                showError(resp, "DB is not available");
            }
        }

    }

    public void getAllExchangeRates(HttpServletResponse resp){
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM exchangerates";
        try {
            prStatement = connection.getConnection().prepareStatement(query);
            ResultSet resultSet = prStatement.executeQuery();
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
            prStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException | IOException e) {
            resp.setStatus(500);
            showError(resp, String.valueOf(e));
        }
    }

    public void getSpecificExchangeRate(HttpServletRequest req, HttpServletResponse resp){
        DBConnection connection = new DBConnection();
        String path = req.getRequestURI();
        String[] urls = path.split("/");
        if(urls[urls.length - 1].length() != 6 || !urls[urls.length - 2].equals("exchangeRate")){
            resp.setStatus(400);
            showError(resp, "Wrong Request");
        } else {
            String currCode1 = urls[urls.length - 1].substring(0,3).toUpperCase();
            String currCode2 = urls[urls.length - 1].substring(3).toUpperCase();
            int currID1 = findCurrencyByCode(currCode1);
            int currID2 = findCurrencyByCode(currCode2);
            String query = "SELECT * FROM exchangerates WHERE " +
                    "(basecurrencyid = ? AND targetcurrencyid = ?) OR " +
                    "(basecurrencyid = ? AND targetcurrencyid = ?)";
            try {
                prStatement = connection.getConnection().prepareStatement(query);
                prStatement.setInt(1,currID1);
                prStatement.setInt(2,currID2);
                prStatement.setInt(3,currID2);
                prStatement.setInt(4,currID1);
                ResultSet rs = prStatement.executeQuery();
                if(rs.next()){
                    ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(rs.getInt(1),
                            getCurrencyDTObyID(rs.getInt(2)),getCurrencyDTObyID(rs.getInt(3)),
                            rs.getInt(4));
                    resp.setContentType("application/json; charset=UTF-8");
                    out = resp.getWriter();
                    String out1 = objectMapper.writeValueAsString(exchangeRateDTO);
                    out.println(out1);
                    rs.close();
                    prStatement.close();
                    connection.close();
                } else {
                    resp.setStatus(404);
                    showError(resp, "No such currency pair found");
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertNewExchangeRate(HttpServletRequest req, HttpServletResponse resp) {
        DBConnection connection = new DBConnection();
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        int baseCurrencyID = findCurrencyByCode(baseCurrencyCode);
        int targetCurrencyID = findCurrencyByCode(targetCurrencyCode);
        int rate = Integer.parseInt(req.getParameter("rate"));
        if(baseCurrencyID == 0 || targetCurrencyID == 0){
            resp.setStatus(404);
            showError(resp, "No such currency(s) found in DB");
        } else if(isExchangeRateInDB(baseCurrencyID,targetCurrencyID)){
            resp.setStatus(409);
            showError(resp, "That exchange rate already in DB");
        }else {
            String queryInsert = "INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) " +
                    "VALUES (?, ?, ?)";
            String querySelect = "SELECT * FROM exchangerates WHERE basecurrencyid = ? AND " +
                    "targetcurrencyid = ?";
            try {
                prStatement = connection.getConnection().prepareStatement(queryInsert);
                prStatement.setInt(1,baseCurrencyID);
                prStatement.setInt(2,targetCurrencyID);
                prStatement.setInt(3, rate);
                prStatement.executeUpdate();
                prStatement = connection.getConnection().prepareStatement(querySelect);
                prStatement.setInt(1,baseCurrencyID);
                prStatement.setInt(2,targetCurrencyID);
                ResultSet rs = prStatement.executeQuery();
                if(rs.next()){
                    ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(rs.getInt(1),
                            getCurrencyDTObyID(baseCurrencyID), getCurrencyDTObyID(targetCurrencyID),
                            rs.getInt(4));
                    resp.setContentType("application/json; charset=UTF-8");
                    out = resp.getWriter();
                    String out1 = objectMapper.writeValueAsString(exchangeRateDTO);
                    out.println(out1);
                }
                connection.close();
                prStatement.close();
                rs.close();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private boolean isExchangeRateInDB(int baseCurrencyID, int targetCurrencyID) {
        DBConnection connection = new DBConnection();
        try {
            String querySelect = "SELECT * FROM exchangerates WHERE (basecurrencyid = ? AND " +
                    "targetcurrencyid = ?) OR (basecurrencyid = ? AND targetcurrencyid = ?)";
            prStatement = connection.getConnection().prepareStatement(querySelect);
            prStatement.setInt(1,baseCurrencyID);
            prStatement.setInt(2,targetCurrencyID);
            prStatement.setInt(3,targetCurrencyID);
            prStatement.setInt(4,baseCurrencyID);
            ResultSet rs = prStatement.executeQuery();
            if(rs.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findCurrencyByCode(String code) {
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM currencies WHERE code = ?";
        int out = 0;
        try {
            prStatement = connection.getConnection().prepareStatement(query);
            prStatement.setString(1, code);
            ResultSet resultSet = prStatement.executeQuery();
            if(resultSet.next()){
                out = resultSet.getInt(1);
            }
            resultSet.close();
            prStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    private CurrencyDTO getCurrencyDTObyID(int id){
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM currencies WHERE id = ?";
        CurrencyDTO out;
        try {
            prStatement = connection.getConnection().prepareStatement(query);
            prStatement.setInt(1,id);
            ResultSet resultSet = prStatement.executeQuery();
            resultSet.next();
            out = new CurrencyDTO(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            prStatement.close();
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


}
