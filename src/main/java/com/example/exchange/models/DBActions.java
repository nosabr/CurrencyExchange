package com.example.exchange.models;

import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBActions {
    private Statement statement;

    public void getAllCurrencies(HttpServletResponse resp){
        DBConnection connection = new DBConnection(); // подключение к БД
        String query = "SELECT * FROM currencies"; // запрос
        try {
            statement = connection.getConnection().createStatement(); //создаем утверждение?
            ResultSet resultSet = statement.executeQuery(query);



        } catch (SQLException e){

        }
    }
}
