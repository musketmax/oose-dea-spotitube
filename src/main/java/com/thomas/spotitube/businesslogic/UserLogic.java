package com.thomas.spotitube.businesslogic;

import com.thomas.spotitube.businesslogic.interfaces.IUserLogic;
import com.thomas.spotitube.datasource.dao.interfaces.IUserDao;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UnauthorizedException;

import javax.inject.Inject;

public class UserLogic implements IUserLogic {
    @Inject
    private IUserDao userDao;

    public User authenticate(Credentials credentials) throws UnauthorizedException {
        String username = credentials.getUser();
        String password = credentials.getPassword();

        if (!userDao.checkLogin(username, password)) {
            throw new UnauthorizedException();
        }

        return userDao.login(credentials.getUser(), credentials.getPassword());
    }

    public String getUser(String token) throws TokenInvalidException {
        return "not implemented";
    }
}
