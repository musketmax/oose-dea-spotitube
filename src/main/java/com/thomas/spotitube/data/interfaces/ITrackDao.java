package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Track;

import java.util.ArrayList;

public interface ITrackDao {
    int getTotalDurationInSeconds(int[] playlistIds);
    ArrayList<Track> getTracksForPlaylist(int playlistId);
    ArrayList<Track> getAvailableTracksForPlaylist(int playlistId);
    boolean addTrackToPlaylist(int playlistId, Track track);
    boolean deleteTrackFromPlaylist(int playlistId, int trackId);
}
