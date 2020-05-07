package com.thomas.spotitube.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CredentialsTest {
    @Test
    public void TestCreateIsNotNull() {
        Credentials creds = new Credentials();

        assertNotNull(creds);
    }

    @Test
    public void TestGetAndSetProperties() {
        Credentials creds = new Credentials();
        creds.setUser("user");
        creds.setPassword("password");

        assertEquals("user", creds.getUser());
        assertEquals("password", creds.getPassword());
    }
}
