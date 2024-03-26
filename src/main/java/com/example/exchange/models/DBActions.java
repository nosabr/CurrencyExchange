package com.example.exchange.models;



import com.example.exchange.DTO.CurrencyDTO;
import com.example.exchange.DTO.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                        resultSet.getString(2), resultSet.getString(3),
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
