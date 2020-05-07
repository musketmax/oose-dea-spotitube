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
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.GET_USER_BY_USERNAME);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            User user = null;

            if (result.next()) {
                // Remove old token and set new one
                removeToken(result.getInt("id"));
                String token = addToken(result.getInt("id"));

                user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        token
                );

                // Set password so we can check for the validation later
                user.setPassword(result.getString("password"));
            }

            preparedStatement.close();
            connection.close();

            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + singletonDatabaseProperties.connectionString(), e);
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
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.GET_USER);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, token);
            ResultSet result = preparedStatement.executeQuery();

            User user = null;

            if (result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("token")
                );
            }

            preparedStatement.close();
            connection.close();

            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + singletonDatabaseProperties.connectionString(), e);
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
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.TOKEN_EXISTS);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean result = resultSet.next();
            preparedStatement.close();
            connection.close();

            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Remove any existing tokens for user
     *
     * @param userId: int
     * @return void
     */
    public void removeToken(int userId) {
        try {
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.REMOVE_TOKEN);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + singletonDatabaseProperties.connectionString(), e);
        }
    }

    /**
     * Add a token for the user and return it
     *
     * @param userId: int
     * @return void
     */
    public String addToken(int userId) {
        try {
            Connection connection = getConnection();

            final String token = UUID.randomUUID().toString();
            String query = getQuery(DatabaseConstants.ADD_TOKEN);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return token;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }
}

