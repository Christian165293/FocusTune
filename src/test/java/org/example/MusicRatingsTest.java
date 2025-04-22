package org.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MusicRatingsTest {
    private MusicRatings ratings;
    private final String TEST_SONG = "test_song.mp3";

    @BeforeEach
    void setUp() {
        ratings = new MusicRatings();
    }

    @Test
    void addFileToHashMap_shouldAddSongWithDefaultLikedStatus() {
        ratings.addFileToHashMap(TEST_SONG);
        assertTrue(ratings.getSongStatus(TEST_SONG));
    }

    @Test
    void dislikeSong_shouldChangeStatusToDisliked() {
        ratings.addFileToHashMap(TEST_SONG);
        ratings.dislikeSong(TEST_SONG);
        assertFalse(ratings.getSongStatus(TEST_SONG));
    }

    @Test
    void checkForLikedRatings_shouldReturnFalseWhenAllLiked() {
        ratings.addFileToHashMap(TEST_SONG);
        assertFalse(ratings.checkForLikedRatings());
    }

    @Test
    void checkForLikedRatings_shouldReturnTrueWhenAllDisliked() {
        ratings.addFileToHashMap(TEST_SONG);
        ratings.dislikeSong(TEST_SONG);
        assertTrue(ratings.checkForLikedRatings());
    }

    @Test
    void getSongStatus_shouldReturnTrueForUnknownSong() {
        assertTrue(ratings.getSongStatus("unknown_song.mp3"));
    }

    @Test
    void resetAllRatings_shouldSetAllSongsToLiked() {
        ratings.addFileToHashMap(TEST_SONG);
        ratings.dislikeSong(TEST_SONG);
        ratings.resetAllRatings();
        assertTrue(ratings.getSongStatus(TEST_SONG));
    }
}