package com.thomas.spotitube;

import com.thomas.spotitube.domain.Credentials;
import com.thomas.spotitube.exceptions.ForbiddenException;
import com.thomas.spotitube.exceptions.UserNotFoundException;
import com.thomas.spotitube.logic.UserLogic;
import com.thomas.spotitube.services.LoginService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserLogic userLogic;

    private Credentials credentials;
    private JSONObject user;

    @Before
    public void setup() {
        credentials = new Credentials();
        credentials.setUser("admin");
        credentials.setPassword("password");

        user = new JSONObject();
        user.put("user", "admin");
        user.put("token", "1234-1234-1234");
    }

    @Test
    public void init() {
        assertNotNull(loginService);
        assertNotNull(userLogic);
    }

    @Test
    public void TestLoginUserWithCredentials() throws UserNotFoundException, ForbiddenException, InvalidKeySpecException, NoSuchAlgorithmException {
        when(userLogic.authenticate(credentials)).thenReturn(user);

        Response response = loginService.login(credentials);

        verify(userLogic).authenticate(credentials);
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }
}
