package com.example.exchange.dao;

import com.example.exchange.dto.CurrencyDTO;
import com.example.exchange.models.ConnectionManager;
import com.example.exchange.models.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO {
    private static final String FIND_ALL = "SELECT * FROM currencies;";

    public List<CurrencyDTO> findAll() {
        List<CurrencyDTO> currencies = new ArrayList<>();
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
    private CurrencyDTO createCurrency(ResultSet rs) throws SQLException{
        return new CurrencyDTO(rs.getInt(1), rs.getString(2),
                rs.getString(3), rs.getString(4));
    }
}
