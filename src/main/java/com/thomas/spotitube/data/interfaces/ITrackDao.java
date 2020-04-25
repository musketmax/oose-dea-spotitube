package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Track;

import java.util.ArrayList;

public interface ITrackDao {
    ArrayList<Track> getTracksForPlaylists(int[] playlistIds);
    ArrayList<Track> getTracksForPlaylist(int playlistId);
}
