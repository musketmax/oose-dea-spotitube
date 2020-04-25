package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.interfaces.IPlaylistDao;
import com.thomas.spotitube.data.interfaces.ITrackDao;
import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.logic.interfaces.IPlaylistLogic;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PlayListLogic implements IPlaylistLogic {
    @Inject
    private IPlaylistDao playlistDao;

    @Inject
    private ITrackDao trackDao;

    /**
     * Get all playlists belonging to user
     *
     * @param userId: int
     * @return JSONObject
     */
    @Override
    public JSONObject getPlaylists(int userId) {
        ArrayList<Playlist> playlists = this.playlistDao.getPlaylists(userId);
        ArrayList<Track> tracks = this.trackDao.getTracksForPlaylists(playlists.stream().mapToInt(Playlist::getId).toArray());
        int duration = getTotalDurationInSeconds(tracks.stream());

        JSONObject result = new JSONObject();
        result.put("playlists", playlists);
        result.put("length", duration);

        return result;
    }

    /**
     * Calculate total duration of all the playlists
     *
     * @param tracks: Stream<Track>
     * @return int
     */
    private int getTotalDurationInSeconds(Stream<Track> tracks) {
        return tracks.mapToInt(Track::getDuration).sum();
    }
}
