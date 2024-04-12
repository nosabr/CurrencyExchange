package com.example.exchange.dao;

import com.example.exchange.entity.ExchangeRate;
import com.example.exchange.models.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO {
    private static String FIND_ALL = "SELECT * FROM exchangerates";
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    public List<ExchangeRate> findAll() {
        try {
            Connection connection = ConnectionManager.getConnection();
            List<ExchangeRate> exchangeRateList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                exchangeRateList.add(new ExchangeRate(rs.getInt(1),
                        currenciesDAO.findById(rs.getInt(2)),
                        currenciesDAO.findById(rs.getInt(3)),
                        rs.getDouble(4)));
            }
            connection.close();
            rs.close();
            preparedStatement.close();
            return exchangeRateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
