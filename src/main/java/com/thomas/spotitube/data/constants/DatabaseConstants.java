package com.thomas.spotitube.data.constants;

public final class DatabaseConstants {
    // User
    public static final String getUser = "SELECT users.username, users.id, tokens.token FROM users INNER JOIN tokens ON users.id = tokens.user_id WHERE tokens.token = ?";
    public static final String authenticateUser = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String userExists = "SELECT username FROM users WHERE username = ?";
    public static final String removeToken = "DELETE FROM tokens WHERE user_id = ?";
    public static final String addToken = "INSERT INTO tokens (user_id, token) VALUES (?, ?)";
    public static final String tokenExists = "SELECT token FROM tokens WHERE token = ?";

    // Playlist
    public static final String getPlaylists = "SELECT * FROM playlists";
    public static final String getPlaylist = "SELECT * FROM playlists WHERE id = ?";

    // Track
    public static final String getTracks = "SELECT * FROM tracks";
    public static final String getTrack = "SELECT * FROM tracks WHERE id = ?";
    public static final String getTracksForPlaylists = "SELECT * FROM TRACKS WHERE playlist_id IN(?)";
    public static final String getTracksForPlaylist = "SELECT * FROM TRACKS WHERE playlist_id = ?";
}
