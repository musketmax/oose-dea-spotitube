package com.thomas.spotitube.data;

import com.thomas.spotitube.data.interfaces.IDatabase;
import com.thomas.spotitube.data.properties.SingletonDatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Database implements IDatabase {
    protected Logger logger;
    protected SingletonDatabaseProperties singletonDatabaseProperties;
    protected Connection connection;

    public Database() {
        logger = Logger.getLogger(getClass().getName());
        singletonDatabaseProperties = SingletonDatabaseProperties.getInstance();

        loadDriver(singletonDatabaseProperties);
        connection = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: cannot establish a connection.", e);
        }

        return connection;
    }

    public void loadDriver(SingletonDatabaseProperties props) {
        try {
            Class.forName(props.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error: cannot load driver " + singletonDatabaseProperties.driver(), e);
        }
    }
}
