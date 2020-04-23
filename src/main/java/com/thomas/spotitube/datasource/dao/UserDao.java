package com.thomas.spotitube.datasource.dao;

import com.thomas.spotitube.datasource.dao.interfaces.IUserDao;
import com.thomas.spotitube.datasource.database.Database;
import com.thomas.spotitube.domain.User;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class UserDao extends Database implements IUserDao {

    public User login(String username, String password) {
        final String token = UUID.randomUUID().toString();
        final String query = "SELECT users.username FROM users" +
                " INNER JOIN tokens ON users.username = tokens.username" +
                " WHERE users.username = ? AND users.password = ?";

        deleteExistingToken(username);
        createToken(token, username);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();

            User user = new User();

            while (result.next()) {
                user.setToken(token);
                user.setUser(result.getString("username"));
            }

            preparedStatement.close();

            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    public boolean checkLogin(String username, String password) {
        final String query = "SELECT username FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean result = resultSet.first();
            preparedStatement.close();

            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    private void deleteExistingToken(String username) {
        final String query = "DELETE FROM tokens WHERE username = ?";

        try {
            Connection connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
        }
    }

    private void createToken(String token, String username) {
        final String query = "INSERT INTO tokens (token, username) VALUES (?, ?)";

        try {
            Connection connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
        }
    }
}

