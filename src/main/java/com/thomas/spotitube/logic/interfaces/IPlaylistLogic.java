package com.thomas.spotitube.logic.interfaces;

import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.exceptions.ServerErrorException;
import org.json.simple.JSONObject;

public interface IPlaylistLogic {
    JSONObject getPlaylistsForUser(int userId);
    JSONObject deletePlaylist(int userId, int playlistId) throws ServerErrorException;
    JSONObject addPlaylist(int userId, Playlist playlist) throws ServerErrorException;
    JSONObject updatePlaylist(int userId, int playlistId, Playlist playlist) throws ServerErrorException;
}
