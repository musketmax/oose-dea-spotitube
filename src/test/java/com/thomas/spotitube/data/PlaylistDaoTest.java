package com.thomas.spotitube.data;

import com.thomas.spotitube.domain.Playlist;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistDaoTest {
    @InjectMocks
    private PlaylistDao playlistDao;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private PlaylistDao playlistDaoMock;

    private int userId;
    private int playlistId;
    private int duration;
    private Playlist playlist;

    @Before
    public void setup() throws SQLException {
        userId = 1;
        playlistId = 1;
        duration = 10;

        playlist = new Playlist();
        playlist.setProperties(playlistId, "Rock", true);

        playlistDaoMock = Mockito.spy(playlistDao);
        doReturn(c).when(playlistDaoMock).getConnection();

        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    @Test
    public void init() {
        assertNotNull(playlistDaoMock);
        assertNotNull(c);
        assertNotNull(stmt);
        assertNotNull(rs);
    }

    @Test
    public void TestGetPlaylists() throws SQLException {
        // Allow looping for 2 times..
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("user_id")).thenReturn(userId);
        when(rs.getInt("id")).thenReturn(playlist.getId());
        when(rs.getString("name")).thenReturn(playlist.getName());

        ArrayList<Playlist> expectedPlaylists = new ArrayList<>();
        expectedPlaylists.add(playlist);
        expectedPlaylists.add(playlist);

        ArrayList<Playlist> result = playlistDaoMock.getPlaylists(userId);

        verify(rs, times(3)).next();
        verify(rs, times(2)).getInt("user_id");
        verify(rs, times(2)).getInt("id");
        verify(rs, times(2)).getString("name");
        assertNotNull(result);
        assertEquals(expectedPlaylists.get(0).getName(), result.get(0).getName());
        assertEquals(expectedPlaylists.get(1).getName(), result.get(1).getName());
    }

    @Test
    public void TestAddPlaylist() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);
        JSONObject body = new JSONObject();
        body.put("name", playlist.getName());
        body.put("user_id", userId);

        boolean result = playlistDaoMock.addPlaylist(userId, body);

        verify(stmt).executeUpdate();
        assertTrue(result);
    }

    @Test
    public void TestUpdatePlaylist() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        boolean result = playlistDaoMock.updatePlaylist(userId, playlistId, playlist);

        verify(stmt).executeUpdate();
        assertTrue(result);
    }

    @Test
    public void TestDeletePlaylist() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        boolean result = playlistDaoMock.deletePlaylist(playlistId);

        verify(stmt).executeUpdate();
        assertTrue(result);
    }
}
