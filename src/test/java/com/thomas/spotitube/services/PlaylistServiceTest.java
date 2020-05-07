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
public class PlaylistServiceTest {
    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlayListLogic playListLogic;

    @Mock
    private UserLogic userLogic;

    @Mock
    private TrackLogic trackLogic;

    private String token;
    private User user;
    private JSONObject playlists;
    private JSONObject playlistsAfterAdd;
    private JSONObject playlistsAfterUpdate;
    private JSONObject tracks;
    private JSONObject tracksAfteradd;
    private Playlist newPlaylist;
    private Playlist updatePlaylist;
    private Track newTrack;

    private Response getPlaylistsResponse;
    private Response addPlaylistResponse;
    private Response updatePlaylistResponse;
    private Response getTracksForPlaylistResponse;
    private Response addTrackToPlaylistResponse;

    @Before
    public void setup() {
        token = "1234-1234-1234-1234";
        user = new User(1, "admin", token);
        playlists = new JSONObject();
        playlistsAfterAdd = new JSONObject();
        playlistsAfterUpdate = new JSONObject();
        tracks = new JSONObject();
        tracksAfteradd = new JSONObject();

        ArrayList<Playlist> list = new ArrayList<>();
        ArrayList<Playlist> listAfterAdd = new ArrayList<>();
        ArrayList<Playlist> listAfterUpdate = new ArrayList<>();
        ArrayList<Track> trackList = new ArrayList<>();
        ArrayList<Track> trackListAfterAdd = new ArrayList<>();

        Playlist playlist1 = new Playlist();
        Playlist playlist2 = new Playlist();
        newPlaylist = new Playlist();
        updatePlaylist = new Playlist();
        Track track1 = new Track();
        Track track2 = new Track();
        newTrack = new Track();

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

        newTrack.setProperties(
                3,
                "Africa",
                "Rosanna",
                680,
                "Some album",
                8,
                new Date(),
                "Some description"
        );

        playlist1.setProperties(1, "Rock", true);
        playlist2.setProperties(2, "Pop", false);
        newPlaylist.setProperties(3, "R&B", true);
        updatePlaylist.setProperties(1, "THIS IS CHANGED", true);

        list.add(playlist1);
        list.add(playlist2);

        listAfterAdd.add(playlist1);
        listAfterAdd.add(playlist2);
        listAfterAdd.add(newPlaylist);

        listAfterUpdate.add(updatePlaylist);
        listAfterUpdate.add(playlist2);

        trackList.add(track1);
        trackList.add(track2);

        trackListAfterAdd.add(track1);
        trackListAfterAdd.add(track2);
        trackListAfterAdd.add(newTrack);

        playlists.put("playlists", list);
        playlistsAfterAdd.put("playlists", listAfterAdd);
        playlistsAfterUpdate.put("playlists", listAfterUpdate);
        tracks.put("tracks", trackList);
        tracksAfteradd.put("tracks", trackListAfterAdd);

        getPlaylistsResponse = Response.status(Response.Status.OK).entity(playlists).build();
        addPlaylistResponse = Response.status(Response.Status.CREATED).entity(playlistsAfterAdd).build();
        updatePlaylistResponse = Response.status(Response.Status.OK).entity(playlistsAfterUpdate).build();
        getTracksForPlaylistResponse = Response.status(Response.Status.OK).entity(tracks).build();
        addTrackToPlaylistResponse = Response.status(Response.Status.OK).entity(tracksAfteradd).build();
    }

    @Test
    public void init() {
        assertNotNull(playlistService);
        assertNotNull(playListLogic);
        assertNotNull(userLogic);
        assertNotNull(trackLogic);
    }

    @Test
    public void TestGetAllPlaylists() throws TokenInvalidException {
        when(userLogic.getUser(token)).thenReturn(user);
        when(playListLogic.getPlaylistsForUser(user.getId())).thenReturn(playlists);

        Response response = playlistService.get(token);

        verify(userLogic).validateToken(token);
        verify(userLogic).getUser(token);
        verify(playListLogic).getPlaylistsForUser(user.getId());
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(getPlaylistsResponse.getStatus(), response.getStatus());
        assertEquals(getPlaylistsResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestCreateNewPlaylist() throws TokenInvalidException, ServerErrorException {
        when(userLogic.getUser(token)).thenReturn(user);
        when(playListLogic.addPlaylist(user.getId(), newPlaylist)).thenReturn(playlistsAfterAdd);

        Response response = playlistService.create(token, newPlaylist);

        verify(userLogic).validateToken(token);
        verify(userLogic).getUser(token);
        verify(playListLogic).addPlaylist(user.getId(), newPlaylist);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(addPlaylistResponse.getStatus(), response.getStatus());
        assertEquals(addPlaylistResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestUpdatePlaylist() throws TokenInvalidException, ServerErrorException {
        when(userLogic.getUser(token)).thenReturn(user);
        when(playListLogic.updatePlaylist(user.getId(), updatePlaylist.getId(), updatePlaylist)).thenReturn(playlistsAfterUpdate);

        Response response = playlistService.update(token, updatePlaylist.getId(), updatePlaylist);

        verify(userLogic).validateToken(token);
        verify(userLogic).getUser(token);
        verify(playListLogic).updatePlaylist(user.getId(), updatePlaylist.getId(), updatePlaylist);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(updatePlaylistResponse.getStatus(), response.getStatus());
        assertEquals(updatePlaylistResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestDeletePlaylist() throws TokenInvalidException, ServerErrorException {
        when(userLogic.getUser(token)).thenReturn(user);
        when(playListLogic.deletePlaylist(user.getId(), newPlaylist.getId())).thenReturn(playlists);

        Response response = playlistService.delete(token, newPlaylist.getId());

        verify(userLogic).validateToken(token);
        verify(userLogic).getUser(token);
        verify(playListLogic).deletePlaylist(user.getId(), newPlaylist.getId());
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(getPlaylistsResponse.getStatus(), response.getStatus());
        assertEquals(getPlaylistsResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestGetTracksForPlaylist() throws TokenInvalidException {
        when(trackLogic.getTracksForPlaylist(newPlaylist.getId())).thenReturn(tracks);

        Response response = playlistService.tracks(token, newPlaylist.getId());

        verify(userLogic).validateToken(token);
        verify(trackLogic).getTracksForPlaylist(newPlaylist.getId());
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(getTracksForPlaylistResponse.getStatus(), response.getStatus());
        assertEquals(getTracksForPlaylistResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestAddTrackToPlaylist() throws ServerErrorException, TokenInvalidException {
        when(trackLogic.addTrackToPlaylist(newPlaylist.getId(), newTrack)).thenReturn(tracksAfteradd);

        Response response = playlistService.addTrack(token, newPlaylist.getId(), newTrack);

        verify(userLogic).validateToken(token);
        verify(trackLogic).addTrackToPlaylist(newPlaylist.getId(), newTrack);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(addTrackToPlaylistResponse.getStatus(), response.getStatus());
        assertEquals(addTrackToPlaylistResponse.getEntity(), response.getEntity());
    }

    @Test
    public void TestDeleteTrackFromPlaylist() throws ServerErrorException, TokenInvalidException {
        when(trackLogic.deleteTrackFromPlaylist(newPlaylist.getId(), newTrack.getId())).thenReturn(tracks);

        Response response = playlistService.deleteTrack(token, newPlaylist.getId(), newTrack.getId());

        verify(userLogic).validateToken(token);
        verify(trackLogic).deleteTrackFromPlaylist(newPlaylist.getId(), newTrack.getId());
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(getTracksForPlaylistResponse.getStatus(), response.getStatus());
        assertEquals(getTracksForPlaylistResponse.getEntity(), response.getEntity());
    }
}
