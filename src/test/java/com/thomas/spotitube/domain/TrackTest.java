package com.thomas.spotitube.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TrackTest {
    @Test
    public void TestCreateIsNotNull() {
        Track track = new Track();

        assertNotNull(track);
    }

    @Test
    public void TestGetAndSetProperties() {
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
        track.setOfflineAvailable(true);

        assertEquals(1, track.getId());
        assertEquals("Sweet child o' mine", track.getTitle());
        assertEquals("Guns 'n Roses", track.getPerformer());
        assertEquals(550, track.getDuration());
        assertEquals("Some album", track.getAlbum());
        assertEquals(5, track.getPlaycount());
        assertEquals(new Date().toString(), track.getPublicationDate());
        assertEquals("Some description", track.getDescription());
        assertTrue(track.isOfflineAvailable());
    }

    @Test
    public void TestGetAndSetPropertiesViaIndividualMethods() {
        Track track = new Track();
        track.setId(1);
        track.setTitle("Sweet child o' mine");
        track.setPerformer("Guns 'n Roses");
        track.setDuration(550);
        track.setAlbum("Some album");
        track.setPlaycount(5);
        track.setPublicationDate(new Date());
        track.setDescription("Some description");
        track.setOfflineAvailable(true);

        assertEquals(1, track.getId());
        assertEquals("Sweet child o' mine", track.getTitle());
        assertEquals("Guns 'n Roses", track.getPerformer());
        assertEquals(550, track.getDuration());
        assertEquals("Some album", track.getAlbum());
        assertEquals(5, track.getPlaycount());
        assertEquals(new Date().toString(), track.getPublicationDate());
        assertEquals("Some description", track.getDescription());
        assertTrue(track.isOfflineAvailable());
    }
}
