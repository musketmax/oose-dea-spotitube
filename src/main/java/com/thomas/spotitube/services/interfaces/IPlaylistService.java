package com.thomas.spotitube.services.interfaces;

import com.thomas.spotitube.domain.Playlist;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IPlaylistService {
    @GET
    Response get(@QueryParam("token") String token);

    @POST
    Response create(@QueryParam("token") String token, Playlist playlist);

    @PUT
    @Path("{playlistId}")
    Response update(@QueryParam("token") String token, Playlist playlist);

    @DELETE
    @Path("{playlistId}")
    Response delete(@QueryParam("token") String token, @PathParam("playlistId") int playlistId);

    @GET
    @Path("{playlistId}/tracks")
    Response tracks(@QueryParam("token") String token, @PathParam("playlistId") int playlistId);
}
