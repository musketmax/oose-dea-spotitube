package com.thomas.spotitube.logic.interfaces;

import org.json.simple.JSONObject;

public interface ITrackLogic {
    JSONObject getTracksForPlaylist(int playlistId);
}
