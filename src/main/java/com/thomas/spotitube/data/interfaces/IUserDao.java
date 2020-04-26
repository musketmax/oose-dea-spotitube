package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.domain.User;

public interface IUserDao {
    User getUserByUsername(String username);
    User getUserByToken(String token);
    boolean doesTokenExist(String token);
}

