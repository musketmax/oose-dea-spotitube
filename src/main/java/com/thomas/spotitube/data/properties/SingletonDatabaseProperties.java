package com.thomas.spotitube.data.properties;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonDatabaseProperties {
    private Properties props;
    private static SingletonDatabaseProperties instance;

    private SingletonDatabaseProperties() {
        Logger logger = Logger.getLogger(getClass().getName());
        props = new Properties();

        try {
            props.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("database.properties")));
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "-- Error: cannot find database configuration!", exception);
        }
    }

    public static SingletonDatabaseProperties getInstance() {
        if (instance == null) {
            instance = new SingletonDatabaseProperties();
        }

        return instance;
    }

    public String driver()
    {
        return props.getProperty("driver");
    }

    public String connectionString()
    {
        return props.getProperty("connectionString");
    }

    public String getQuery(String key) {
        return props.getProperty(key);
    }
}