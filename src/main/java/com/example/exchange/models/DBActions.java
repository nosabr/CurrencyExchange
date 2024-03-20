package com.example.exchange.models;

import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBActions {
    private Statement statement;

    public void getCurrencies(HttpServletResponse resp){
        DBConnection connection = new DBConnection();
        String query = "SELECT * FROM currencies";
        try {
            statement = connection.getConnection().createStatement();


        } catch (SQLException e){

        }
    }
}
