package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.interfaces.ITrackDao;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.logic.interfaces.ITrackLogic;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;

public class TrackLogic implements ITrackLogic {
    @Inject
    private ITrackDao trackDao;

    public JSONObject getTracksForPlaylist(int playlistId) {
        ArrayList<Track> tracks = trackDao.getTracksForPlaylist(playlistId);
        JSONObject items = new JSONObject();
        items.put("tracks", tracks);

        return items;
    }
}
