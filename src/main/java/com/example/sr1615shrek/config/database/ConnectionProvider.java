package com.example.sr1615shrek.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

// Code from first classes of Technologie Obiektowe
public final class ConnectionProvider {

    private static final String JDBC_DRIVER = "org.sqlite.JDBC";

    private static final String JDBC_ADDRESS = "jdbc:sqlite:entities_positions.db";

    private static final Logger logger = Logger.getGlobal();

    private static Optional<Connection> connection = Optional.empty();

    static {
        init(JDBC_ADDRESS);
    }

    public static void init(final String jdbcAddress) {
        try {
            close();
            logger.info("Loading driver");
            Class.forName(JDBC_DRIVER);
            connection = Optional.of(DriverManager.getConnection(jdbcAddress));
            logger.info("Connection created");
        } catch (Exception e) {
            logger.info("Error during initialization: " + e.getMessage());
        }
    }

    private ConnectionProvider() {
        throw new UnsupportedOperationException();
    }

    public static Connection getConnection() {
        return connection.orElseThrow(() -> new RuntimeException("Connection is not valid."));
    }

    public static void close() throws SQLException {
        if (connection.isPresent()) {
            logger.info("Closing connection");
            connection.get().close();
            connection = Optional.empty();
        }
    }

}
