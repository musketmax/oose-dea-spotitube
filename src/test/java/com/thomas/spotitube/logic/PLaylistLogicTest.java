package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.PlaylistDao;
import com.thomas.spotitube.data.TrackDao;
import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.ServerErrorException;
import com.thomas.spotitube.logic.PlayListLogic;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PLaylistLogicTest {
    @InjectMocks
    private PlayListLogic playListLogic;

    @Mock
    private PlaylistDao playlistDao;

    @Mock
    private TrackDao trackDao;

    private int userId;
    private int duration;
    private int durationAfterDelete;
    private int durationAfterAdd;
    private String token;

    private User user;
    private Playlist newPLaylist;
    private Playlist updatePlaylist;
    private ArrayList<Playlist> playlists;
    private ArrayList<Playlist> resultPlaylistsAfterDelete;
    private ArrayList<Playlist> resultPlaylistsAfterAdd;
    private ArrayList<Playlist> resultPlaylistsAfterUpdate;

    @Before
    public void setup() {
        userId = 1;
        duration = 15;
        user = new User(userId, "admin", token);
        token = "1234-1234-1234-1234";

        playlists = new ArrayList<>();

        Playlist playlist1 = new Playlist();
        Playlist playlist2 = new Playlist();

        playlist1.setProperties(1, "Rock", true);
        playlist1.setProperties(2, "Pop", false);

        playlists.add(playlist1);
        playlists.add(playlist2);

        resultPlaylistsAfterDelete = new ArrayList<>();
        resultPlaylistsAfterDelete.add(playlists.get(1));
        durationAfterDelete = 10;

        newPLaylist = new Playlist();
        newPLaylist.setProperties(3, "R&B", true);
        resultPlaylistsAfterAdd = new ArrayList<Playlist>();
        resultPlaylistsAfterAdd.add(playlist1);
        resultPlaylistsAfterAdd.add(playlist2);
        resultPlaylistsAfterAdd.add(newPLaylist);
        durationAfterAdd = 20;

        updatePlaylist = new Playlist();
        updatePlaylist.setProperties(1, "THIS TITLE IS CHANGED", true);
        resultPlaylistsAfterUpdate = new ArrayList<>();
        resultPlaylistsAfterUpdate.add(updatePlaylist);
        resultPlaylistsAfterUpdate.add(playlist2);
    }

    @Test
    public void init() {
        assertNotNull(playListLogic);
        assertNotNull(playlistDao);
        assertNotNull(trackDao);
    }

    @Test
    public void TestGetAllPlaylistsForUser() {
        when(playlistDao.getPlaylists(userId)).thenReturn(playlists);
        when(trackDao.getTotalDurationInSeconds(playlists.get(0).getId())).thenReturn(5);
        when(trackDao.getTotalDurationInSeconds(playlists.get(1).getId())).thenReturn(10);

        JSONObject result = playListLogic.getPlaylistsForUser(userId);

        verify(playlistDao).getPlaylists(userId);
        verify(trackDao).getTotalDurationInSeconds(playlists.get(0).getId());
        verify(trackDao).getTotalDurationInSeconds(playlists.get(1).getId());
        assertNotNull(result);
        assertEquals(playlists, result.get("playlists"));
        assertEquals(duration, result.get("length"));
    }

    @Test
    public void TestDeletePlaylistForUser() throws ServerErrorException {
        when(playlistDao.deletePlaylist(playlists.get(0).getId())).thenReturn(true);
        when(playlistDao.getPlaylists(userId)).thenReturn(resultPlaylistsAfterDelete);
        when(trackDao.getTotalDurationInSeconds(playlists.get(1).getId())).thenReturn(10);

        JSONObject result = playListLogic.deletePlaylist(userId, playlists.get(0).getId());

        verify(playlistDao).deletePlaylist(playlists.get(0).getId());
        verify(playlistDao).getPlaylists(userId);
        assertNotNull(result);
        assertEquals(resultPlaylistsAfterDelete, result.get("playlists"));
        assertEquals(durationAfterDelete, result.get("length"));
    }

    @Test
    public void TestAddPlaylistForUser() throws ServerErrorException {
        JSONObject newPlaylistObject = new JSONObject();
        newPlaylistObject.put("name", newPLaylist.getName());
        newPlaylistObject.put("user_id", userId);
        when(playlistDao.addPlaylist(userId, newPlaylistObject)).thenReturn(true);
        when(playlistDao.getPlaylists(userId)).thenReturn(resultPlaylistsAfterAdd);
        when(trackDao.getTotalDurationInSeconds(resultPlaylistsAfterAdd.get(0).getId())).thenReturn(5);
        when(trackDao.getTotalDurationInSeconds(resultPlaylistsAfterAdd.get(1).getId())).thenReturn(10);
        when(trackDao.getTotalDurationInSeconds(resultPlaylistsAfterAdd.get(2).getId())).thenReturn(5);

        JSONObject result = playListLogic.addPlaylist(userId, newPLaylist);

        verify(playlistDao).addPlaylist(userId, newPlaylistObject);
        verify(playlistDao).getPlaylists(userId);
        assertNotNull(result);
        assertEquals(resultPlaylistsAfterAdd, result.get("playlists"));
        assertEquals(durationAfterAdd, result.get("length"));
    }

    @Test
    public void TestUpdatePlaylistForUser() throws ServerErrorException {
        when(playlistDao.updatePlaylist(userId, updatePlaylist.getId(), updatePlaylist)).thenReturn(true);
        when(playlistDao.getPlaylists(userId)).thenReturn(resultPlaylistsAfterUpdate);
        when(trackDao.getTotalDurationInSeconds(resultPlaylistsAfterUpdate.get(0).getId())).thenReturn(5);
        when(trackDao.getTotalDurationInSeconds(resultPlaylistsAfterUpdate.get(1).getId())).thenReturn(10);

        JSONObject result = playListLogic.updatePlaylist(userId, updatePlaylist.getId(), updatePlaylist);

        verify(playlistDao).updatePlaylist(userId, updatePlaylist.getId(), updatePlaylist);
        verify(playlistDao).getPlaylists(userId);
        assertNotNull(result);
        assertEquals(resultPlaylistsAfterUpdate, result.get("playlists"));
        assertEquals(duration, result.get("length"));
    }
}
