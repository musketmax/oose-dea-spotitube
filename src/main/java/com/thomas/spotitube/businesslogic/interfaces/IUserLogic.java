package com.thomas.spotitube.businesslogic.interfaces;

import com.thomas.spotitube.businesslogic.Credentials;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UnauthorizedException;

public interface IUserLogic {
    User authenticate(Credentials credentials) throws UnauthorizedException;
    String getUser(String token) throws TokenInvalidException;
}
