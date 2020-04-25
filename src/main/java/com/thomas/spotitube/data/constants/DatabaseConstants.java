package com.thomas.spotitube.data.constants;

public final class DatabaseConstants {
    public static final String authenticateUser = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String userExists = "SELECT username FROM users WHERE username = ?";
    public static final String removeToken = "DELETE FROM tokens WHERE user_id = ?";
    public static final String addToken = "INSERT INTO tokens (user_id, token) VALUES (?, ?)";
    public static final String tokenExists = "SELECT token FROM tokens WHERE token = ?";
}
