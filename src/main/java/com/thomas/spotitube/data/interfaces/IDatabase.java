package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.data.properties.SingletonDatabaseProperties;

import java.sql.Connection;

public interface IDatabase {
    Connection getConnection();
    void loadDriver(SingletonDatabaseProperties singletonDatabaseProperties);
}
