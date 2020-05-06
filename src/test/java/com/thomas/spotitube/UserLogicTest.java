package com.thomas.spotitube;

import com.thomas.spotitube.data.UserDao;
import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import com.thomas.spotitube.logic.UserLogic;
import com.thomas.spotitube.utilities.PasswordHashing;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLogicTest {
    @InjectMocks
    private UserLogic userLogic;

    @Mock
    private UserDao userDao;

    private Credentials credentials;
    private User user;
    private String token = "1234-1234-1234-1234";
    private String HASH = "1000:be8528029730569a3c0602cc6922bd7c:8953047bf438ad3840f4c6ce6de39fadaf0193245150b949a6f747da39cfed2396ecb4169088afd91ecf4c816f8657518eadde174de2b798f62fc887da7aadf8";

    @Before
    public void setup() {
        credentials = new Credentials();
        credentials.setUser("admin");
        credentials.setPassword("password");

        user = new User(1, "admin", token);
        user.setPassword(HASH);
    }

    @Test
    public void init() {
        assertNotNull(userLogic);
        assertNotNull(userDao);
    }

    @Test
    public void TestPasswordHashing() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String originalPassword = "password";

        String hashedPassword = PasswordHashing.generatePasswordHash(originalPassword);
        boolean isValid = PasswordHashing.validatePassword(originalPassword, hashedPassword);
        boolean isInvalid = PasswordHashing.validatePassword("faulty", hashedPassword);

        assertTrue(isValid);
        assertFalse(isInvalid);
    }

    @Test
    public void TestUserIsAuthenticatedWithPasswordHashing() throws UserNotFoundException, ForbiddenException, InvalidKeySpecException, NoSuchAlgorithmException {
        when(userDao.getUserByUsername(user.getUser())).thenReturn(user);

        JSONObject result = userLogic.authenticate(credentials);

        verify(userDao).getUserByUsername(user.getUser());
        assertNotNull(result);
        assertEquals(user.getUser(), result.get("user"));
        assertEquals(user.getToken(), result.get("token"));
    }

    @Test
    public void TestValidateToken() throws TokenInvalidException {
        when(userDao.doesTokenExist(token)).thenReturn(true);

        userLogic.validateToken(token);

        verify(userDao).doesTokenExist(token);
    }

    @Test
    public void TestGetUserByToken() throws TokenInvalidException {
        when(userDao.getUserByToken(token)).thenReturn(user);

        User result = userLogic.getUser(token);

        verify(userDao).getUserByToken(token);
        assertNotNull(result);
        assertEquals(user.getUser(), result.getUser());
    }
}
