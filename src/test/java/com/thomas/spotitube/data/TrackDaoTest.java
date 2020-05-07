package com.thomas.spotitube.data;

import com.thomas.spotitube.domain.Track;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrackDaoTest {
    @InjectMocks
    private TrackDao trackDao;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private TrackDao trackDaoMock;

    private int playlistId;
    private int duration;
    private Track track;

    @Before
    public void setup() throws SQLException {
        playlistId = 1;
        duration = 10;

        track = new Track();
        track.setProperties(
                3,
                "Sweet child o' mine",
                "Guns 'n Roses",
                550,
                "Some album",
                5,
                new Date(),
                "Some description"
        );
        track.setOfflineAvailable(true);

        trackDaoMock = Mockito.spy(trackDao);
        doReturn(c).when(trackDaoMock).getConnection();

        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    @Test
    public void init() {
        assertNotNull(trackDaoMock);
        assertNotNull(c);
        assertNotNull(stmt);
        assertNotNull(rs);
    }

    @Test
    public void TestGetTotalDurationInSeconds() throws SQLException {
        when(rs.getInt("duration")).thenReturn(duration);

        int result = trackDaoMock.getTotalDurationInSeconds(playlistId);

        verify(rs).next();
        verify(rs).getInt("duration");
        assertEquals(duration, result);
    }

    @Test
    public void TestGetTracksForPlaylist() throws SQLException {
        doReturn(track).when(trackDaoMock).makeNewTrack(rs, true);
        ArrayList<Track> expectedTracks = new ArrayList<>();
        expectedTracks.add(track);
        expectedTracks.add(track);
        // Allow while loop to continue for 2 times, before breaking out of it with falsy return value
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        ArrayList<Track> result = trackDaoMock.getTracksForPlaylist(playlistId);

        verify(rs, times(3)).next();
        verify(trackDaoMock, times(2)).makeNewTrack(rs, true);
        assertNotNull(result);
        assertEquals(expectedTracks, result);
    }

    @Test
    public void TestGetAvailableTracksForPlaylist() throws SQLException {
        doReturn(track).when(trackDaoMock).makeNewTrack(rs, false);
        ArrayList<Track> expectedTracks = new ArrayList<>();
        expectedTracks.add(track);
        expectedTracks.add(track);
        // Allow while loop to continue for 2 times, before breaking out of it with falsy return value
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        ArrayList<Track> result = trackDaoMock.getAvailableTracksForPlaylist(playlistId);

        verify(rs, times(3)).next();
        verify(trackDaoMock, times(2)).makeNewTrack(rs, false);
        assertNotNull(result);
        assertEquals(expectedTracks, result);
    }

    @Test
    public void TestAddTrackToPlaylist() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        boolean result = trackDaoMock.addTrackToPlaylist(playlistId, track);

        verify(stmt).executeUpdate();
        assertTrue(result);
    }

    @Test
    public void TestDeleteTrackFromPlaylist() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        boolean result = trackDaoMock.deleteTrackFromPlaylist(playlistId, track.getId());

        verify(stmt).executeUpdate();
        assertTrue(result);
    }

    @Test
    public void TestMakeNewTrack() throws SQLException {
        when(rs.getInt("id")).thenReturn(track.getId());
        when(rs.getString("title")).thenReturn(track.getTitle());
        when(rs.getString("performer")).thenReturn(track.getPerformer());
        when(rs.getInt("duration")).thenReturn(track.getDuration());
        when(rs.getString("album")).thenReturn(track.getAlbum());
        when(rs.getInt("playcount")).thenReturn(track.getPlaycount());
        when(rs.getDate("publicationDate")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getString("description")).thenReturn(track.getDescription());
        when(rs.getInt("offline_available")).thenReturn(1);

        Track result = trackDaoMock.makeNewTrack(rs, true);

        verify(rs).getInt("id");
        verify(rs).getString("title");
        verify(rs).getString("performer");
        verify(rs).getInt("duration");
        verify(rs).getString("album");
        verify(rs).getInt("playcount");
        verify(rs).getDate("publicationDate");
        verify(rs).getString("description");
        verify(rs).getInt("offline_available");
        assertNotNull(result);
        assertEquals(track.getId(), result.getId());
        assertEquals(track.getTitle(), result.getTitle());
        assertEquals(track.getPerformer(), result.getPerformer());
        assertEquals(track.getDuration(), result.getDuration());
        assertEquals(track.getAlbum(), result.getAlbum());
        assertEquals(track.getPlaycount(), result.getPlaycount());
        assertEquals(track.getDescription(), result.getDescription());
        assertEquals(track.isOfflineAvailable(), result.isOfflineAvailable());
    }
}
