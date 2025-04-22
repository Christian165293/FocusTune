package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PlayerStateManagerTest {
    private PlayerStateManager stateManager;

    @BeforeEach
    void setUp() {
        stateManager = new PlayerStateManager();
    }

    @Test
    void isPlaying_shouldReturnFalseByDefault() {
        assertFalse(stateManager.isPlaying());
    }

    @Test
    void setPlaying_shouldChangePlayingState() {
        stateManager.setPlaying(true);
        assertTrue(stateManager.isPlaying());
        stateManager.setPlaying(false);
        assertFalse(stateManager.isPlaying());
    }
}