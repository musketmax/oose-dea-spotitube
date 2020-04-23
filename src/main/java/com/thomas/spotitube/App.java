package com.thomas.spotitube;

import com.thomas.spotitube.datasource.database.Database;
import com.thomas.spotitube.datasource.database.SingletonDatabaseProperties;

public class App {
    public static void main(String[] args) {
        SingletonDatabaseProperties.getInstance();
        Database database = new Database();
    }
}
