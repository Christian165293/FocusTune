package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MusicRatingsTest {
    private MusicRatings musicRatings;

    @BeforeEach
    public void setUp() {
        musicRatings = new MusicRatings();
    }

    @Test
    public void testAddFileToHashMap() {
        musicRatings.addFileToHashMap("song1.mp3");
        assertTrue(musicRatings.getSongStatus("song1.mp3"));
    }

    @Test
    public void testDislikeSong() {
        musicRatings.addFileToHashMap("song1.mp3");
        musicRatings.dislikeSong("song1.mp3");
        assertFalse(musicRatings.getSongStatus("song1.mp3"));
    }

    @Test
    public void testGetSongStatusDefault() {
        assertTrue(musicRatings.getSongStatus("nonexistent.mp3"));
    }

    @Test
    public void testMultipleSongs() {
        musicRatings.addFileToHashMap("song1.mp3");
        musicRatings.addFileToHashMap("song2.mp3");
        musicRatings.dislikeSong("song1.mp3");

        assertFalse(musicRatings.getSongStatus("song1.mp3"));
        assertTrue(musicRatings.getSongStatus("song2.mp3"));
    }
}