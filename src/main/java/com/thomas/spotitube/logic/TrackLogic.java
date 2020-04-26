package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.interfaces.ITrackDao;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.exceptions.ServerErrorException;
import com.thomas.spotitube.logic.interfaces.ITrackLogic;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;

public class TrackLogic implements ITrackLogic {
    @Inject
    private ITrackDao trackDao;

    /**
     * Get all tracks belonging to playlist
     *
     * @param playlistId: int
     * @return JSONObject
     */
    @Override
    public JSONObject getTracksForPlaylist(int playlistId) {
        ArrayList<Track> tracks = trackDao.getTracksForPlaylist(playlistId);
        JSONObject items = new JSONObject();
        items.put("tracks", tracks);

        return items;
    }

    /**
     * Get all tracks available for playlist
     *
     * @param playlistId: int
     * @return JSONObject
     */
    @Override
    public JSONObject getAvailableTracksForPlaylist(int playlistId) {
        ArrayList<Track> tracks = trackDao.getAvailableTracksForPlaylist(playlistId);
        JSONObject items = new JSONObject();
        items.put("tracks", tracks);

        return items;
    }

    /**
     * Add a track to playlist
     *
     * @param playlistId: int
     * @param track: Track
     * @return
     */
    @Override
    public JSONObject addTrackToPlaylist(int playlistId, Track track) throws ServerErrorException {
        if (trackDao.addTrackToPlaylist(playlistId, track)) {
            return getTracksForPlaylist(playlistId);
        } else {
            throw new ServerErrorException();
        }
    }

    /**
     * Remove the track from playlist
     *
     * @param playlistId: int
     * @param trackId: int
     * @return JSONObject
     * @throws ServerErrorException
     */
    @Override
    public JSONObject deleteTrackFromPlaylist(int playlistId, int trackId) throws ServerErrorException {
        if (trackDao.deleteTrackFromPlaylist(playlistId, trackId)) {
            return getTracksForPlaylist(playlistId);
        } else {
            throw new ServerErrorException();
        }
    }
}
