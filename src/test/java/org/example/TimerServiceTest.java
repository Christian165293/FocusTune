package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimerServiceTest {
    private static class MockTimerListener implements TimerService.TimerListener {
        private int lastTick = -1;

        @Override
        public void onTimerTick(int remainingSeconds) {
            lastTick = remainingSeconds;
        }

        @Override
        public void onTimerComplete() {
        }

        public int getLastTick() {
            return lastTick;
        }

    }

    private TimerService timerService;
    private MockTimerListener listener;

    @BeforeEach
    public void setUp() {
        listener = new MockTimerListener();
        timerService = new TimerService(listener);
    }

    @Test
    public void testInitialState() {
        assertFalse(timerService.isRunning());
    }

    @Test
    public void testStartTimer() {
        timerService.startTimer(5);
        assertTrue(timerService.isRunning());
        assertEquals(5 * 60, listener.getLastTick());
    }

    @Test
    public void testStopTimer() {
        timerService.startTimer(5);
        assertTrue(timerService.isRunning());

        timerService.stopTimer();
        assertFalse(timerService.isRunning());
    }
}