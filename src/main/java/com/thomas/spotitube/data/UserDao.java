package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.interfaces.IUserDao;
import com.thomas.spotitube.domain.User;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class UserDao extends Database implements IUserDao {

    /**
     * Get Use by username
     *
     * @param username: String
     * @return User
     */
    @Override
    public User getUserByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getUserByUsername);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                // Remove old token and set new one
                removeToken(result.getInt("id"));
                String token = addToken(result.getInt("id"));

                User user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        token
                );

                // Set password so we can check for the validation later
                user.setPassword(result.getString("password"));

                return user;
            }

            preparedStatement.close();

            return null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * Get user by token
     *
     * @param token: String
     * @return User
     */
    @Override
    public User getUserByToken(String token) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getUser);
            preparedStatement.setString(1, token);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("token")
                );

                return user;
            }

            preparedStatement.close();

            return null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * See if token exists
     *
     * @param token: String
     * @return boolean
     */
    @Override
    public boolean doesTokenExist(String token) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.tokenExists);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean result = resultSet.next();
            preparedStatement.close();

            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Remove any existing tokens for user
     *
     * @param userId: int
     * @return void
     */
    private void removeToken(int userId) {
        try {
            Connection connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.removeToken);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
        }
    }

    /**
     * Add a token for the user and return it
     *
     * @param userId: int
     * @return void
     */
    private String addToken(int userId) {
        final String token = UUID.randomUUID().toString();

        try {
            Connection connection = DriverManager.getConnection(singletonDatabaseProperties.connectionString());
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.addToken);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            return token;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }
}

