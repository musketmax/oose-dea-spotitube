package com.thomas.spotitube;

import com.thomas.spotitube.data.UserDao;
import com.thomas.spotitube.data.properties.SingletonDatabaseProperties;

public class App {
    // Run this instance of App to test for a solid Database connection.
    public static void main(String[] args) {
        SingletonDatabaseProperties.getInstance();
        UserDao userdao = new UserDao();

        // If the above instantiation succeeds, we have connected to the Database.
        System.out.println("Connected to database!");
    }
}
