package com.example.exchange.models;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
//    private Connection connection = null;
//    public DBConnection(){
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Exchange", "postgres", "1969");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private final String driverName = "org.sqlite.JDBC";
    private Connection connection = null;

    public DBConnection() {
        try {
            URL resource = DBConnection.class.getClassLoader().getResource("exchange.db");
            String path = null;
            try {
                path = new File(resource.toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            Class.forName(driverName);
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return connection;
    }
    public void close() throws SQLException{
        connection.close();
    }
}

