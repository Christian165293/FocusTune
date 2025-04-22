package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicRatingsTest {
    private MusicRatings ratings;

    @BeforeEach
    public void setUp() {
        ratings = new MusicRatings();
    }

    @Test
    public void testAddFile() {
        ratings.addFileToHashMap("song1.mp3");
        assertTrue(ratings.getSongStatus("song1.mp3"));
    }

    @Test
    public void testDislikeSong() {
        ratings.addFileToHashMap("song1.mp3");
        ratings.dislikeSong("song1.mp3");
        assertFalse(ratings.getSongStatus("song1.mp3"));
    }

    @Test
    public void testNonExistentSongDefaultsToLiked() {
        assertTrue(ratings.getSongStatus("nonexistent.mp3"));
    }

    @Test
    public void testCheckForLikedRatings_AllLiked() {
        ratings.addFileToHashMap("song1.mp3");
        ratings.addFileToHashMap("song2.mp3");

        // If no songs are disliked, should return true (all songs are liked)
        assertFalse(ratings.checkForLikedRatings());
    }

    @Test
    public void testCheckForLikedRatings_NoneLiked() {
        ratings.addFileToHashMap("song1.mp3");
        ratings.addFileToHashMap("song2.mp3");

        ratings.dislikeSong("song1.mp3");
        ratings.dislikeSong("song2.mp3");

        // If all songs are disliked, should return true
        assertTrue(ratings.checkForLikedRatings());
    }

    @Test
    public void testResetAllRatings() {
        ratings.addFileToHashMap("song1.mp3");
        ratings.addFileToHashMap("song2.mp3");

        ratings.dislikeSong("song1.mp3");
        ratings.dislikeSong("song2.mp3");

        ratings.resetAllRatings();

        assertTrue(ratings.getSongStatus("song1.mp3"));
        assertTrue(ratings.getSongStatus("song2.mp3"));
    }
}