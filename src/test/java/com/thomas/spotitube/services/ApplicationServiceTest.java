package com.thomas.spotitube.services;

import com.thomas.spotitube.services.ApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {
    @InjectMocks
    private ApplicationService applicationService;

    @Test
    public void init() {
        assertNotNull(applicationService);
    }

    @Test
    public void TestGoodResponseFromBaseEndpoint() {
        Response response = applicationService.app();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }
}
