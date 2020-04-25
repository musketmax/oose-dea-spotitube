package com.thomas.spotitube.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IPlaylistService {
    @GET
    Response playlists(@QueryParam("token") String token);

    @GET
    @Path("{playlistId}")
    Response playlist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId);

    @GET
    @Path("{playlistId}/tracks")
    Response tracks(@QueryParam("token") String token, @PathParam("playlistId") int playlistId);
}
