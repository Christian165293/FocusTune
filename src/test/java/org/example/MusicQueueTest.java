package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MusicQueueTest {
    private MusicQueue musicQueue;

    @BeforeEach
    public void setUp() {
        musicQueue = new MusicQueue();
    }

    @Test
    public void testIsEmptyOnCreation() {
        assertTrue(musicQueue.isEmpty());
    }

    @Test
    public void testPeekOnEmptyQueue() {
        assertThrows(NullPointerException.class, () -> musicQueue.peek());
    }

    @Test
    public void testAddAndPeek() {
        musicQueue.add("song1.mp3");
        assertFalse(musicQueue.isEmpty());
        assertEquals("song1.mp3", musicQueue.peek());
    }

    @Test
    public void testAddMultipleAndPeek() {
        musicQueue.add("song1.mp3");
        musicQueue.add("song2.mp3");
        assertEquals("song1.mp3", musicQueue.peek());
    }

    @Test
    public void testRemove() {
        musicQueue.add("song1.mp3");
        musicQueue.add("song2.mp3");
        musicQueue.remove();
        assertEquals("song2.mp3", musicQueue.peek());
    }

    @Test
    public void testRemoveAll() {
        musicQueue.add("song1.mp3");
        musicQueue.remove();
        assertTrue(musicQueue.isEmpty());
    }

    @Test
    public void testRemoveOnEmptyQueue() {
        assertThrows(NullPointerException.class, () -> musicQueue.remove());
    }
}