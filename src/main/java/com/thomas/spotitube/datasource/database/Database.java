package com.thomas.spotitube.datasource.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    protected Logger logger;
    protected SingletonDatabaseProperties singletonDatabaseProperties;
    protected Connection connection;

    public Database() {
        logger = Logger.getLogger(getClass().getName());
        logger.log(Level.ALL, "YEAH");
        singletonDatabaseProperties = SingletonDatabaseProperties.getInstance();

        loadDriver(singletonDatabaseProperties);
        connection = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
            logger.log(Level.ALL, "YEAH 2");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: cannot establish a connection.", e);
        }

        return connection;
    }

    public void loadDriver(SingletonDatabaseProperties singletonDatabaseProperties) {
        try {
            Class.forName(singletonDatabaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error: cannot load driver " + singletonDatabaseProperties.driver(), e);
        }
    }
}
