package com.thomas.spotitube.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistTest {

    private Track track;
    private ArrayList<Track> tracks;

    @Before
    public void setup() {
        Track track = new Track();
        track.setProperties(
                1,
                "Sweet child o' mine",
                "Guns 'n Roses",
                550,
                "Some album",
                5,
                new Date(),
                "Some description"
        );

        ArrayList<Track> tracks = new ArrayList<>();

        tracks.add(track);
    }

    @Test
    public void TestCreateIsNotNull() {
        Playlist playlist = new Playlist();

        assertNotNull(playlist);
    }

    @Test
    public void TestGetAndSetProperties() {
        Playlist playlist = new Playlist();
        playlist.setProperties(1, "Rock", true);
        playlist.setTracks(tracks);

        assertEquals(1, playlist.getId());
        assertEquals("Rock", playlist.getName());
        assertTrue(playlist.isOwner());
        assertEquals(tracks, playlist.getTracks());
    }

    @Test
    public void TestGetAndSetPropertiesViaIndividualMethods() {
        Playlist playlist = new Playlist();
        playlist.setId(1);
        playlist.setName("Rock");
        playlist.setOwner(true);
        playlist.setTracks(tracks);

        assertEquals(1, playlist.getId());
        assertEquals("Rock", playlist.getName());
        assertTrue(playlist.isOwner());
        assertEquals(tracks, playlist.getTracks());
    }
}
