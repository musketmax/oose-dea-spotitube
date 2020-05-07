package com.thomas.spotitube.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Test
    public void TestCreateIsNotNull() {
        User user = new User(1, "admin", "1234");

        assertNotNull(user);
    }

    @Test
    public void TestGetAndSetProperties() {
        User user = new User(1, "admin", "1234");
        user.setPassword("password");

        assertEquals(1, user.getId());
        assertEquals("admin", user.getUser());
        assertEquals("1234", user.getToken());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void TestGetAndSetPropertiesViaIndividualMethods() {
        User user = new User(1, "admin", "1234");
        user.setId(2);
        user.setUser("user");
        user.setToken("12345");
        user.setPassword("password1");

        assertEquals(2, user.getId());
        assertEquals("user", user.getUser());
        assertEquals("12345", user.getToken());
        assertEquals("password1", user.getPassword());
    }
}
