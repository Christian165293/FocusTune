package org.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicQueueTest {
    private MusicQueue queue;

    @BeforeEach
    void setUp() {
        queue = new MusicQueue();
    }

    @Test
    void isEmpty_shouldReturnTrueForNewQueue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalseForNonEmptyQueue() {
        queue.add("song1.mp3");
        assertFalse(queue.isEmpty());
    }

    @Test
    void peek_shouldReturnFirstElement() {
        queue.add("song1.mp3");
        queue.add("song2.mp3");
        assertEquals("song1.mp3", queue.peek());
    }

    @Test
    void peek_shouldThrowWhenEmpty() {
        assertThrows(NullPointerException.class, () -> queue.peek());
    }

    @Test
    void add_shouldIncreaseSize() {
        queue.add("song1.mp3");
        assertEquals(1, queue.getSize());
        queue.add("song2.mp3");
        assertEquals(2, queue.getSize());
    }

    @Test
    void remove_shouldDecreaseSize() {
        queue.add("song1.mp3");
        queue.add("song2.mp3");
        queue.remove();
        assertEquals(1, queue.getSize());
    }

    @Test
    void remove_shouldMakeQueueEmptyWhenLastElementRemoved() {
        queue.add("song1.mp3");
        queue.remove();
        assertTrue(queue.isEmpty());
    }

    @Test
    void getSize_shouldReturnCorrectSize() {
        assertEquals(0, queue.getSize());
        queue.add("song1.mp3");
        assertEquals(1, queue.getSize());
        queue.add("song2.mp3");
        assertEquals(2, queue.getSize());
        queue.remove();
        assertEquals(1, queue.getSize());
    }
}