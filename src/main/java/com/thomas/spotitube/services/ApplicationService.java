package com.thomas.spotitube.services;

import com.thomas.spotitube.businesslogic.Credentials;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class ApplicationService {
    @GET
    public Response app() {
        String message = "Spotitube is running!";
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
