package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.logic.interfaces.IPlaylistLogic;
import com.thomas.spotitube.logic.interfaces.ITrackLogic;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import com.thomas.spotitube.services.interfaces.IPlaylistService;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class PlaylistService implements IPlaylistService {
    @Inject
    private IUserLogic userLogic;

    @Inject
    private IPlaylistLogic playlistLogic;

    @Inject
    private ITrackLogic trackLogic;

    public Response playlists(String token) {
        try {
            userLogic.validateToken(token);
            User user = userLogic.getUser(token);

            JSONObject playlists = playlistLogic.getPlaylists(user.getId());

            return Response
                    .status(Response.Status.OK)
                    .entity(playlists)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        }
    }

    @Override
    public Response playlist(String token, int playlistId) {
        return null;
    }

    @Override
    public Response tracks(String token, int playlistId) {
        try {
            userLogic.validateToken(token);

            JSONObject tracks = trackLogic.getTracksForPlaylist(playlistId);

            return Response
                    .status(Response.Status.OK)
                    .entity(tracks)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        }
    }
}
