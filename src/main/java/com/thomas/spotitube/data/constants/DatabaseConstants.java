package com.thomas.spotitube.data.constants;

public final class DatabaseConstants {
    // Users
    public static final String getUser = "SELECT users.username, users.id, tokens.token FROM users INNER JOIN tokens ON users.id = tokens.user_id WHERE tokens.token = ?";
    public static final String authenticateUser = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String userExists = "SELECT username FROM users WHERE username = ?";
    public static final String removeToken = "DELETE FROM tokens WHERE user_id = ?";
    public static final String addToken = "INSERT INTO tokens (user_id, token) VALUES (?, ?)";
    public static final String tokenExists = "SELECT token FROM tokens WHERE token = ?";

    // Playlists
    public static final String getPlaylists = "SELECT * FROM playlists";
    public static final String deletePlaylist = "DELETE FROM playlists WHERE id = ?";
    public static final String addPlaylist = "INSERT INTO playlists (name, user_id) VALUES (?, ?)";
    public static final String updatePlaylist= "UPDATE playlists SET name = ? WHERE id = ?";

    // Tracks
    public static final String getTotalTrackDuration = "SELECT sum(tracks.duration) AS 'duration'\n" +
            " FROM playlist_tracks_pivot\n" +
            " INNER JOIN tracks ON playlist_tracks_pivot.track_id = tracks.id\n" +
            " WHERE playlist_tracks_pivot.playlist_id IN (?);";
    public static final String getTracksForPlaylist = "SELECT tracks.*, playlist_tracks_pivot.offline_available FROM tracks\n" +
            " LEFT JOIN playlist_tracks_pivot ON tracks.id = playlist_tracks_pivot.track_id\n" +
            " WHERE playlist_tracks_pivot.playlist_id = ?\n" +
            " ORDER BY tracks.id;";
    public static final String getAvailableTracksForPlaylist = "SELECT tracks.* FROM tracks\n" +
            " LEFT JOIN playlist_tracks_pivot ON tracks.id = playlist_tracks_pivot.track_id\n" +
            " WHERE tracks.id NOT IN (\n" +
            " SELECT tracks.id\n" +
            " FROM playlist_tracks_pivot\n" +
            " INNER JOIN tracks ON playlist_tracks_pivot.track_id = tracks.id\n" +
            "    INNER JOIN playlists ON playlist_tracks_pivot.playlist_id = playlists.id\n" +
            " WHERE playlist_tracks_pivot.playlist_id = ? \n" +
            " ) GROUP BY tracks.id ORDER BY tracks.id";
    public static final String addTrackToPlaylist = "INSERT INTO playlist_tracks_pivot (playlist_id, track_id, offline_available) VALUES (?, ?, ?);";
    public static final String deleteTrackFromPlaylist = "DELETE FROM playlist_tracks_pivot WHERE playlist_id = ? AND track_id = ?";
}
