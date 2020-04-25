package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Playlist;

import java.util.ArrayList;

public interface IPlaylistDao {
    ArrayList<Playlist> getPlaylists(int userId);
}
