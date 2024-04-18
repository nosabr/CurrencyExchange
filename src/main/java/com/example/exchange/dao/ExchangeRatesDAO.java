package com.example.exchange.dao;

import com.example.exchange.DTO.RequestExchangeRateDTO;
import com.example.exchange.entity.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO {
    private static String FIND_ALL = "SELECT * FROM exchangerates";
    private  static String INSERT = "INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) " +
            "VALUES (?,?,?)";
    private static final String FIND_PAIR_BY_CODES =
            "SELECT er.id, er.basecurrencyid, er.targetcurrencyid, er.rate " +
                    "FROM exchangerates er " +
                    "INNER JOIN currencies base_curr ON er.basecurrencyid = base_curr.id " +
                    "INNER JOIN currencies target_curr ON er.targetcurrencyid = target_curr.id " +
                    "WHERE base_curr.code = ? AND target_curr.code = ?;";
    private static final String UPDATE = "UPDATE exchangerates SET rate = ? " +
            "WHERE basecurrencyid IN (SELECT id FROM currencies WHERE code = ?) " +
            "AND targetcurrencyid IN (SELECT id FROM currencies WHERE code = ?)";
//
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();

    public static void update(RequestExchangeRateDTO requestExchangeRateDTO) {
        String baseCurrencyCode = requestExchangeRateDTO.getBaseCurrencyCode();
        String targetCurrencyCode = requestExchangeRateDTO.getTargetCurrencyCode();
        double rate = Double.parseDouble(requestExchangeRateDTO.getRate());
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setDouble(1,rate);
            preparedStatement.setString(2,baseCurrencyCode);
            preparedStatement.setString(3,targetCurrencyCode);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
//
    public ExchangeRate findByCodes(RequestExchangeRateDTO request) {
        String baseCurrencyCode = request.getBaseCurrencyCode();
        String targetCurrencyCode = request.getTargetCurrencyCode();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PAIR_BY_CODES);
            preparedStatement.setString(1,baseCurrencyCode);
            preparedStatement.setString(2,targetCurrencyCode);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                ExchangeRate exchangeRate = createExchangeRate(rs);
                connection.close();
                preparedStatement.close();
                rs.close();
                return exchangeRate;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void add (RequestExchangeRateDTO request){
        int baseCurrencyId = currenciesDAO.findByCode(request.getBaseCurrencyCode()).getId();
        int targetCurrencyId = currenciesDAO.findByCode(request.getTargetCurrencyCode()).getId();
        double rate = Double.parseDouble(request.getRate());
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setInt(1,baseCurrencyId);
            preparedStatement.setInt(2,targetCurrencyId);
            preparedStatement.setDouble(3, rate);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isPairInDB(RequestExchangeRateDTO request){
        String currencyCode1 = request.getBaseCurrencyCode();
        String currencyCode2 = request.getTargetCurrencyCode();
        RequestExchangeRateDTO request2 = new RequestExchangeRateDTO();
        request2.setBaseCurrencyCode(currencyCode2);
        request2.setTargetCurrencyCode(currencyCode1);
        return findByCodes(request) != null || findByCodes(request2) != null;
    }
    private ExchangeRate createExchangeRate(ResultSet rs) {
        try {
            return new ExchangeRate(rs.getInt(1), rs.getInt(2),
                    rs.getInt(3), rs.getDouble(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}