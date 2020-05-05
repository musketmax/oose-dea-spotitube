package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.services.interfaces.IApplicationService;

import javax.ws.rs.core.Response;

public class ApplicationService implements IApplicationService {
    @Override
    public Response app() {
        return Response
                .status(Response.Status.OK)
                .entity(HttpMessageConstants.APPLICATION)
                .build();
    }
}
