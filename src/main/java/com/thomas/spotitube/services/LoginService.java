package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import org.json.simple.JSONObject;

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
            JSONObject user = userLogic.authenticate(credentials);

            return Response
                    .status(Response.Status.OK)
                    .entity(user)
                    .build();
        } catch (UserNotFoundException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(HttpMessageConstants.USER_NOT_FOUND)
                    .build();
        } catch (ForbiddenException e) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(HttpMessageConstants.FORBIDDEN)
                    .build();
        }
    }
}
