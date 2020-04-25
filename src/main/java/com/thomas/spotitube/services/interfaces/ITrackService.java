package com.thomas.spotitube.services.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ITrackService {
    @GET
    Response getTracks(@QueryParam("token") String token);

    @GET
    @Path("{ trackId }")
    Response getTrack(@QueryParam("token") String token, @PathParam("trackId") int trackId);

//    @PUT
//    @Path("{ trackId }")
//    Response updateTrack(@QueryParam("token") String token, @PathParam("trackId") int trackId);
}
