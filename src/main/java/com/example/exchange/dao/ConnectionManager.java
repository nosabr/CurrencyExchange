package com.example.exchange.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionManager {
    private static HikariDataSource connectionPool;

    static {
        initializeConnectionPool();
    }

    private static void initializeConnectionPool() {
        String path = Objects.requireNonNull(ConnectionManager.class.getClassLoader()
                .getResource("exchange.db")).toString();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + path);
        config.setMaximumPoolSize(10);
        connectionPool = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    static void closeConnection(){
        connectionPool.close();
    }
}
