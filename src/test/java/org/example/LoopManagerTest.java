package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoopManagerTest {
    private LoopManager loopManager;

    @BeforeEach
    public void setUp() {
        loopManager = new LoopManager();
    }

    @Test
    public void testInitialStateIsNotLooping() {
        // Default state should be not looping
        assertFalse(loopManager.isLoopEnabled());
    }

    @Test
    public void testEnableLoop() {
        loopManager.enableLoop();
        assertTrue(loopManager.isLoopEnabled());
    }

    @Test
    public void testDisableLoop() {
        // First enable loop
        loopManager.enableLoop();
        assertTrue(loopManager.isLoopEnabled());

        // Then disable it
        loopManager.disableLoop();
        assertFalse(loopManager.isLoopEnabled());
    }
}