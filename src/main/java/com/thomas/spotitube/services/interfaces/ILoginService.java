package com.thomas.spotitube.services.interfaces;

import com.thomas.spotitube.domain.Credentials;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ILoginService {
    @POST
    Response login(Credentials credentials);
}
