package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MusicQueueTest {
    private MusicQueue queue;

    @BeforeEach
    public void setUp() {
        queue = new MusicQueue();
    }

    @Test
    public void testNewQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testAddSingleSong() {
        queue.add("song1.mp3");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.getSize());
        assertEquals("song1.mp3", queue.peek());
    }

    @Test
    public void testAddMultipleSongs() {
        queue.add("song1.mp3");
        queue.add("song2.mp3");
        queue.add("song3.mp3");

        assertEquals(3, queue.getSize());
        assertEquals("song1.mp3", queue.peek());
    }

    @Test
    public void testRemove() {
        queue.add("song1.mp3");
        queue.add("song2.mp3");

        assertEquals("song1.mp3", queue.peek());
        queue.remove();
        assertEquals("song2.mp3", queue.peek());
        assertEquals(1, queue.getSize());
    }

    @Test
    public void testRemoveUntilEmpty() {
        queue.add("song1.mp3");
        queue.add("song2.mp3");

        queue.remove();
        queue.remove();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }
}