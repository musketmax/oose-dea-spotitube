package com.thomas.spotitube.services;

import com.thomas.spotitube.businesslogic.Credentials;
import com.thomas.spotitube.businesslogic.interfaces.IUserLogic;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.UnauthorizedException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {
    @Inject
    private IUserLogic userLogic;

    @POST
    public Response login(Credentials credentials) {
        try {
            User user = userLogic.authenticate(credentials);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
