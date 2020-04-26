package com.thomas.spotitube.logic.interfaces;

import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.exceptions.ServerErrorException;
import org.json.simple.JSONObject;

public interface ITrackLogic {
    JSONObject getTracksForPlaylist(int playlistId);
    JSONObject getAvailableTracksForPlaylist(int playlistId);
    JSONObject addTrackToPlaylist(int playlistId, Track track) throws ServerErrorException;
    JSONObject deleteTrackFromPlaylist(int playlistId, int trackId) throws ServerErrorException;
}
