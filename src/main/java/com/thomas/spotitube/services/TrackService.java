package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.logic.interfaces.ITrackLogic;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import com.thomas.spotitube.services.interfaces.ITrackService;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class TrackService implements ITrackService {
    @Inject
    private IUserLogic userLogic;

    @Inject
    private ITrackLogic trackLogic;

    @Override
    public Response get(String token, int playlistId) {
        try {
            userLogic.validateToken(token);

            JSONObject tracks = trackLogic.getAvailableTracksForPlaylist(playlistId);

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
