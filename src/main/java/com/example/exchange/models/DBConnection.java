package com.example.exchange.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private Connection connection = null;
    private void GetConnectionToDB() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Exchange", "postgres", "1969");
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("No Connection");
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
}

