package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStateManagerTest {
    private PlayerStateManager stateManager;

    @BeforeEach
    public void setUp() {
        stateManager = new PlayerStateManager();
    }

    @Test
    public void testDefaultStateIsNotPlaying() {
        assertFalse(stateManager.isPlaying());
    }

    @Test
    public void testSetPlaying() {
        stateManager.setPlaying(true);
        assertTrue(stateManager.isPlaying());

        stateManager.setPlaying(false);
        assertFalse(stateManager.isPlaying());
    }
}