package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.logic.interfaces.IPlaylistLogic;
import com.thomas.spotitube.logic.interfaces.IUserLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistService {
    @Inject
    private IUserLogic userLogic;

    @Inject
    private IPlaylistLogic playlistLogic;

    @GET
    public Response playlists(@QueryParam("token") String token) {
        try {
            userLogic.validateToken(token);

            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        }
    }
}
