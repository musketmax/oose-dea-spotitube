package com.thomas.spotitube.logic.interfaces;

import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import org.json.simple.JSONObject;

public interface IUserLogic {
    JSONObject authenticate(Credentials credentials) throws ForbiddenException, UserNotFoundException;
    void validateToken(String token) throws TokenInvalidException;
}
