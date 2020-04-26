package com.thomas.spotitube.logic;

import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import com.thomas.spotitube.data.interfaces.IUserDao;
import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.utilities.PasswordHashing;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class UserLogic implements IUserLogic {
    @Inject
    private IUserDao userDao;

    /**
     * Authenticate user
     *
     * @param credentials: Credentials
     * @return JSONObject
     *
     * @throws ForbiddenException
     * @throws UserNotFoundException
     */
    @Override
    public JSONObject authenticate(Credentials credentials) throws ForbiddenException, UserNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = userDao.getUserByUsername(credentials.getUser());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!PasswordHashing.validatePassword(credentials.getPassword(), user.getPassword())) {
            throw new ForbiddenException();
        }

        JSONObject json = new JSONObject();
        json.put("user", user.getUser());
        json.put("token", user.getToken());

        return json;
    }

    /**
     * Validate token
     *
     * @param token: String
     * @return void
     *
     * @throws TokenInvalidException
     */
    @Override
    public void validateToken(String token) throws TokenInvalidException {
        if (!userDao.doesTokenExist(token)) {
            throw new TokenInvalidException();
        }
    }

    /**
     * Get user by token
     *
     * @param token: String
     * @return User
     * @throws TokenInvalidException
     */
    @Override
    public User getUser(String token) throws TokenInvalidException {
        User user = userDao.getUserByToken(token);

        if (user == null) {
            throw new TokenInvalidException();
        }

        return user;
    }
}
