package com.thomas.spotitube.logic.interfaces;

import org.json.simple.JSONObject;

public interface IPlaylistLogic {
    JSONObject getPlaylists(int userId);
}
