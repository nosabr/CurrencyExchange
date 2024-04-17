package com.example.exchange.dao;

import com.example.exchange.DTO.RequestExchangeRateDTO;
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
    private static final String FIND_BY_CODE_PAIR =
            "SELECT er.id, er.base_currency_id, er.target_currency_id, er.rate " +
            "FROM exchange_rates er " +
            "INNER JOIN currencies base_curr ON er.base_currency_id = base_curr.id " +
            "INNER JOIN currencies target_curr ON er.target_currency_id = target_curr.id " +
            "WHERE base_curr.code = ? AND target_curr.code = ?;";

    CurrenciesDAO currenciesDAO = new CurrenciesDAO();
    public List<ExchangeRate> findAll() {
        try {
            Connection connection = ConnectionManager.getConnection();
            List<ExchangeRate> exchangeRateList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                exchangeRateList.add(createExchangeRate(rs));
            }
            connection.close();
            rs.close();
            preparedStatement.close();
            return exchangeRateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ExchangeRate findByCodes(RequestExchangeRateDTO request) {
        String baseCurrencyCode = request.getBaseCurrencyCode();
        String targetCurrencyCode = request.getTargetCurrencyCode();
        return new ExchangeRate(1,1,1,1);
    }

    private ExchangeRate createExchangeRate(ResultSet rs){
        try {
            return new ExchangeRate(rs.getInt(1), rs.getInt(2),
                    rs.getInt(3), rs.getDouble(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
