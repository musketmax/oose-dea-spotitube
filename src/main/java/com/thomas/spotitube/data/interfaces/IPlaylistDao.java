package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Playlist;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface IPlaylistDao {
    ArrayList<Playlist> getPlaylists(int userId);
    boolean deletePlaylist(int playlistId);
    boolean addPlaylist(int userId, JSONObject playlist);
    boolean updatePlaylist(int userId, int playlistId, Playlist playlist);
}
