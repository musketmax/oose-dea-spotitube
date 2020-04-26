package com.thomas.spotitube;

import com.thomas.spotitube.data.UserDao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class App {
    // Run this instance of App to test for a solid Database connection.
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserDao userdao = new UserDao();

        // If the above instantiation succeeds, we have connected to the Database.
        System.out.println("Connection succeeded!");
    }
}
