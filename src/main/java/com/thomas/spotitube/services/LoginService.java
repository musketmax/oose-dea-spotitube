package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import com.thomas.spotitube.services.interfaces.ILoginService;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginService implements ILoginService {
    @Inject
    private IUserLogic userLogic;

    @Override
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
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
