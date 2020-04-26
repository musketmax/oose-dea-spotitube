package com.thomas.spotitube.logic.interfaces;

import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import org.json.simple.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IUserLogic {
    JSONObject authenticate(Credentials credentials) throws ForbiddenException, UserNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException;
    void validateToken(String token) throws TokenInvalidException;
    User getUser(String token) throws TokenInvalidException;
}
