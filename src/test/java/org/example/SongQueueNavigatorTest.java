package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SongQueueNavigatorTest {
    // Test class for SongQueueNavigator that uses MusicQueue and MusicRatings
        private static class MockMusicQueue extends MusicQueue {
            private final String[] songs;
            private int currentIndex = 0;
            private final int size;

            public MockMusicQueue(String[] songs) {
                this.songs = songs;
                this.size = songs.length;
            }

            @Override
            public boolean isEmpty() {
                return size == 0;
            }

            @Override
            public String peek() {
                if (isEmpty()) return null;
                return songs[currentIndex];
            }

            @Override
            public void remove() {
                if (!isEmpty()) {
                    currentIndex = (currentIndex + 1) % songs.length;
                }
            }

            @Override
            public void add(String song) {
                // No-op for tests
            }

            @Override
            public int getSize() {
                return size;
            }
        }

        // Simple mock class for MusicRatings
        private static class MockMusicRatings extends MusicRatings {
            private final boolean[] songLikes;

            public MockMusicRatings(boolean[] songLikes) {
                this.songLikes = songLikes;
            }

            @Override
            public boolean getSongStatus(String fullPathInput) {
                for (int i = 0; i < songLikes.length; i++) {
                    if (fullPathInput.equals("song" + i + ".mp3")) {
                        return songLikes[i];
                    }
                }
                return true;
            }

            @Override
            public boolean checkForLikedRatings() {
                boolean allDisliked = true;
                for (boolean liked : songLikes) {
                    if (liked) {
                        allDisliked = false;
                        break;
                    }
                }
                return allDisliked;
            }

            @Override
            public void resetAllRatings() {
                Arrays.fill(songLikes, true);
            }

        }

        @Test
        public void testGetCurrentSong_EmptyQueue() {
            MockMusicQueue emptyQueue = new MockMusicQueue(new String[0]);
            MockMusicRatings ratings = new MockMusicRatings(new boolean[0]);
            LoopManager loopManager = new LoopManager();
            SongQueueNavigator navigator = new SongQueueNavigator(emptyQueue, ratings,loopManager);
            assertNull(navigator.getCurrentSong());
        }

        @Test
        public void testGetCurrentSong_NonEmptyQueue() {
            MockMusicQueue queue = new MockMusicQueue(new String[]{"song0.mp3"});
            MockMusicRatings ratings = new MockMusicRatings(new boolean[]{true});

            LoopManager loopManager = new LoopManager();
            SongQueueNavigator navigator = new SongQueueNavigator(queue, ratings,loopManager);
            assertEquals("song0.mp3", navigator.getCurrentSong());
        }

        @Test
        public void testFindNextLikedSong_SomeDisliked() {
            MockMusicQueue queue = new MockMusicQueue(new String[]{"song0.mp3", "song1.mp3", "song2.mp3"});
            MockMusicRatings ratings = new MockMusicRatings(new boolean[]{false, true, false});

            LoopManager loopManager = new LoopManager();
            SongQueueNavigator navigator = new SongQueueNavigator(queue, ratings,loopManager);
            assertEquals("song1.mp3", navigator.findNextLikedSong());
        }

        @Test
        public void testMoveToNextSong() {
            MockMusicQueue queue = new MockMusicQueue(new String[]{"song0.mp3", "song1.mp3", "song2.mp3"});
            MockMusicRatings ratings = new MockMusicRatings(new boolean[]{false, true, false});
            LoopManager loopManager = new LoopManager();
            SongQueueNavigator navigator = new SongQueueNavigator(queue, ratings,loopManager);
            navigator.moveToNextSong();
            assertEquals("song1.mp3", navigator.getCurrentSong());
        }
}