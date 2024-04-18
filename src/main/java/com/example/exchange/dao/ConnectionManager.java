package com.example.exchange.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {
    private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
    private static HikariDataSource connectionPool;

    static {
        initializeConnectionPool();
    }

    private static void initializeConnectionPool() {
//        String path = Objects.requireNonNull(ConnectionManager.class.getClassLoader()
//                .getResource("exchange.db")).toString();
        String path = "jdbc:sqlite:C:\\Users\\Sabr\\IdeaProjects\\CurrencyExchange2\\src\\main\\resources\\exchange.db";
        logger.debug("Path to database file: {}", path);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        //config.setJdbcUrl("jdbc:sqlite:" + path);
        config.setJdbcUrl(path);
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
