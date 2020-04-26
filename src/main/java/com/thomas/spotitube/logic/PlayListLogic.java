package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.interfaces.IPlaylistDao;
import com.thomas.spotitube.data.interfaces.ITrackDao;
import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.exceptions.ServerErrorException;
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
    public JSONObject getPlaylistsForUser(int userId) {
        ArrayList<Playlist> playlists = this.playlistDao.getPlaylists(userId);
        int duration = trackDao.getTotalDurationInSeconds(playlists.stream().mapToInt(Playlist::getId).toArray());

        JSONObject result = new JSONObject();
        result.put("playlists", playlists);
        result.put("length", duration);

        return result;
    }

    /**
     * Delete playlist, and return updated list
     *
     * @param playlistId: int
     * @return JSONObject
     * @throws ServerErrorException
     */
    @Override
    public JSONObject deletePlaylist(int userId, int playlistId) throws ServerErrorException {
        if (playlistDao.deletePlaylist(playlistId)) {
            return getPlaylistsForUser(userId);
        } else {
            throw new ServerErrorException();
        }
    }

    /**
     * Create a new playlist for user
     *
     * @param userId:   int
     * @param playlist: Playlist
     * @return JSONObject
     * @throws ServerErrorException
     */
    @Override
    public JSONObject addPlaylist(int userId, Playlist playlist) throws ServerErrorException {
        JSONObject body = new JSONObject();
        body.put("name", playlist.getName());
        body.put("user_id", userId);

        if (playlistDao.addPlaylist(userId, body)) {
            return getPlaylistsForUser(userId);
        } else {
            throw new ServerErrorException();
        }
    }

    /**
     * Update a playlist
     *
     * @param userId:   int
     * @param playlist: Playlist
     * @return JSONObject
     * @throws ServerErrorException
     */
    @Override
    public JSONObject updatePlaylist(int userId, int playlistId, Playlist playlist) throws ServerErrorException {
        if (playlistDao.updatePlaylist(userId, playlistId, playlist)) {
            return getPlaylistsForUser(userId);
        } else {
            throw new ServerErrorException();
        }
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
