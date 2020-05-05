package com.thomas.spotitube;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.services.interfaces.IApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {
    @Mock
    private IApplicationService applicationService;

    @Test
    public void init() {
        assertNotNull(applicationService);
    }

    @Test
    public void testResponseFromApplication() {
        Response validResponse = Response.status(Response.Status.OK).entity(HttpMessageConstants.APPLICATION).build();
        when(applicationService.app()).thenReturn(validResponse);

        Response actualResponse = applicationService.app();

        assertNotNull(actualResponse);

        assertEquals(validResponse.getStatus(), actualResponse.getStatus());
        assertEquals(validResponse.getEntity(), actualResponse.getEntity());
    }
}
