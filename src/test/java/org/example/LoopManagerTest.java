package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LoopManagerTest {
    private LoopManager loopManager;

    @BeforeEach
    void setUp() {
        loopManager = new LoopManager();
    }

    @Test
    void isLoopEnabled_shouldReturnFalseByDefault() {
        assertFalse(loopManager.isLoopEnabled());
    }

    @Test
    void enableLoop_shouldSetLoopEnabledToTrue() {
        loopManager.enableLoop();
        assertTrue(loopManager.isLoopEnabled());
    }

    @Test
    void disableLoop_shouldSetLoopEnabledToFalse() {
        loopManager.enableLoop();
        loopManager.disableLoop();
        assertFalse(loopManager.isLoopEnabled());
    }
}