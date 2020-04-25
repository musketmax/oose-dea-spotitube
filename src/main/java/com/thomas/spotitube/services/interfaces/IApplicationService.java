package com.thomas.spotitube.services.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public interface IApplicationService {
    @GET
    Response app();
}
