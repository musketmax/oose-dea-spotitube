package com.thomas.spotitube.datasource.dao.interfaces;

import com.thomas.spotitube.domain.User;

public interface IUserDao {
    User login(String username, String password);
    boolean checkLogin(String username, String password);
}

