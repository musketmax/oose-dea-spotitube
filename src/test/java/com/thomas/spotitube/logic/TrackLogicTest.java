package com.thomas.spotitube.logic;

import com.thomas.spotitube.data.TrackDao;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.exceptions.ServerErrorException;
import com.thomas.spotitube.logic.TrackLogic;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackLogicTest {
    @InjectMocks
    private TrackLogic trackLogic;

    @Mock
    private TrackDao trackDao;

    private int playlistId;
    private Track newTrack;
    private ArrayList<Track> tracks;
    private ArrayList<Track> tracksAfterAdd;

    @Before
    public void setup() {
        playlistId = 1;

        Track track1 = new Track();
        Track track2 = new Track();
        tracks = new ArrayList<>();

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

        tracks.add(track1);
        tracks.add(track2);

        newTrack = new Track();
        tracksAfterAdd = new ArrayList<>();

        newTrack.setProperties(
                3,
                "Sweet child o' mine",
                "Guns 'n Roses",
                550,
                "Some album",
                5,
                new Date(),
                "Some description"
        );

        tracksAfterAdd.add(track1);
        tracksAfterAdd.add(track2);
        tracksAfterAdd.add(newTrack);
    }

    @Test
    public void init() {
        assertNotNull(trackLogic);
        assertNotNull(trackDao);
    }

    @Test
    public void TestGetAllTracksForPlaylist() {
        when(trackDao.getTracksForPlaylist(playlistId)).thenReturn(tracks);

        JSONObject result = trackLogic.getTracksForPlaylist(playlistId);

        verify(trackDao).getTracksForPlaylist(playlistId);
        assertNotNull(result);
        assertEquals(result.get("tracks"), tracks);
    }

    @Test
    public void TestGetAllAvailableTracksForPlaylist() {
        when(trackDao.getAvailableTracksForPlaylist(playlistId)).thenReturn(tracks);

        JSONObject result = trackLogic.getAvailableTracksForPlaylist(playlistId);

        verify(trackDao).getAvailableTracksForPlaylist(playlistId);
        assertNotNull(result);
        assertEquals(result.get("tracks"), tracks);
    }

    @Test
    public void TestAddTrackToPlaylist() throws ServerErrorException {
        when(trackDao.addTrackToPlaylist(playlistId, newTrack)).thenReturn(true);
        when(trackDao.getTracksForPlaylist(playlistId)).thenReturn(tracksAfterAdd);

        JSONObject result = trackLogic.addTrackToPlaylist(playlistId, newTrack);

        verify(trackDao).addTrackToPlaylist(playlistId, newTrack);
        verify(trackDao).getTracksForPlaylist(playlistId);
        assertNotNull(result);
        assertEquals(tracksAfterAdd, result.get("tracks"));
    }

    @Test
    public void TestDeleteTrackFromPlaylist() throws ServerErrorException {
        when(trackDao.deleteTrackFromPlaylist(playlistId, newTrack.getId())).thenReturn(true);
        when(trackDao.getTracksForPlaylist(playlistId)).thenReturn(tracks);

        JSONObject result = trackLogic.deleteTrackFromPlaylist(playlistId, newTrack.getId());

        verify(trackDao).deleteTrackFromPlaylist(playlistId, newTrack.getId());
        verify(trackDao).getTracksForPlaylist(playlistId);
        assertNotNull(result);
        assertEquals(tracks, result.get("tracks"));
    }
}
