package com.thomas.spotitube.data.interfaces;

import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.domain.User;

public interface IUserDao {
    User authenticate(Credentials credentials);
    boolean doesUserExist(Credentials credentials);
    boolean doesTokenExist(String token);
}

