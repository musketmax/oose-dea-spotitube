package com.thomas.spotitube.services;

import com.thomas.spotitube.services.interfaces.IApplicationService;

import javax.ws.rs.core.Response;

public class ApplicationService implements IApplicationService {
    @Override
    public Response app() {
        String message = "<h1>Spotitube is running a-okay! :)</h1>";
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
