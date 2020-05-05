package com.thomas.spotitube.data.constants;

public final class DatabaseConstants {
    // Users
    public static final String GET_USER = "getUser";
    public static final String GET_USER_BY_USERNAME = "getUserByUsername";
    public static final String REMOVE_TOKEN = "removeToken";
    public static final String ADD_TOKEN = "addToken";
    public static final String TOKEN_EXISTS = "tokenExists";

    // Playlists
    public static final String GET_PLAYLISTS = "getPlaylists";
    public static final String DELETE_PLAYLIST = "deletePlaylist";
    public static final String ADD_PLAYLIST = "addPlaylist";
    public static final String UPDATE_PLAYLIST= "updatePlaylist";

    // Tracks
    public static final String GET_TOTAL_TRACK_DURATION = "getTotalTrackDuration";
    public static final String GET_TRACKS_FOR_PLAYLIST = "getTracksForPlaylist";
    public static final String GET_AVAILABLE_TRACKS_FOR_PLAYLIST = "getAvailableTracksForPlaylist";
    public static final String ADD_TRACK_TO_PLAYLIST = "addTrackToPlaylist";
    public static final String DELETE_TRACK_FROM_PLAYLIST = "deleteTrackFromPlaylist";
}
