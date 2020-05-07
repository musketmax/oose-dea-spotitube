package com.thomas.spotitube.services;

import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.ServerErrorException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.logic.PlayListLogic;
import com.thomas.spotitube.logic.TrackLogic;
import com.thomas.spotitube.logic.UserLogic;
import com.thomas.spotitube.services.PlaylistService;
import com.thomas.spotitube.services.TrackService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceTest {
    @InjectMocks
    private TrackService trackService;

    @Mock
    private TrackLogic trackLogic;

    @Mock
    private UserLogic userLogic;

    private String token;
    private int playlistId = 1;
    private JSONObject tracks;
    private Response tracksResponse;

    @Before
    public void setup() {
        token = "1234-1234-1234-1234";

        tracks = new JSONObject();
        ArrayList<Track> list = new ArrayList<>();
        Track track1 = new Track();
        Track track2 = new Track();

        track1.setProperties(
                1,
                "Sultans of Swing",
                "Dire Straits",
                590,
                "Some album",
                4,
                new Date(),
                "Some description"
        );

        track2.setProperties(
                2,
                "Africa",
                "Toto",
                780,
                "Some album",
                45,
                new Date(),
                "Some description"
        );

        list.add(track1);
        list.add(track2);

        tracks.put("tracks", list);

        tracksResponse = Response.status(Response.Status.OK).entity(tracks).build();
    }

    @Test
    public void init() {
        assertNotNull(trackService);
        assertNotNull(trackLogic);
        assertNotNull(userLogic);
    }

    @Test
    public void TestGetAvailableTracksForPlaylist() throws TokenInvalidException {
        when(trackLogic.getAvailableTracksForPlaylist(playlistId)).thenReturn(tracks);

        Response response = trackService.get(token, playlistId);

        verify(userLogic).validateToken(token);
        verify(trackLogic).getAvailableTracksForPlaylist(playlistId);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(tracksResponse.getStatus(), response.getStatus());
        assertEquals(tracksResponse.getEntity(), response.getEntity());
    }
}
