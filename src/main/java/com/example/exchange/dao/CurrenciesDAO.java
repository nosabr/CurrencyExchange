package com.example.exchange.dao;

import com.example.exchange.entity.Currency;
import com.example.exchange.models.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO {
    private static final String FIND_ALL = "SELECT * FROM currencies;";
    private static final String FIND_BY_CODE = "SELECT * FROM currencies WHERE code = ?";

    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                currencies.add(createCurrency(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    public Currency findByCode(String code){
        Currency currency = null;
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE);
            preparedStatement.setString(1,code);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                currency = createCurrency(rs);
            }
            return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Currency createCurrency(ResultSet rs) throws SQLException{
        return new Currency(rs.getInt(1), rs.getString(2),
                rs.getString(3), rs.getString(4));
    }
}
