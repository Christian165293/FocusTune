package org.example;

//Manages navigation through the MusicQueue
public class SongQueueNavigator {
    private final MusicQueue musicQueue;
    private final MusicRatings musicRatings;
    private String currentSong;

    public SongQueueNavigator(MusicQueue musicQueue, MusicRatings musicRatings) {
        this.musicQueue = musicQueue;
        this.musicRatings = musicRatings;
        // Initialize currentSong
        this.currentSong = musicQueue.isEmpty() ? null : musicQueue.peek();
    }

    public String getCurrentSong() {
        return currentSong;
    }

    //Skips disliked songs when navigating
    public String findNextLikedSong() {
        //Resets all ratings if all songs become disliked
        if (musicRatings.checkForLikedRatings()) {
            musicRatings.resetAllRatings();
            if (musicQueue.isEmpty()) return null;
        }

        String candidate = musicQueue.peek();
        if (musicRatings.getSongStatus(candidate)) {
            return candidate;
        }

        int originalSize = musicQueue.getSize();
        for (int i = 0; i < originalSize; i++) {
            musicQueue.add(musicQueue.peek());
            musicQueue.remove();
            candidate = musicQueue.peek();
            if (musicRatings.getSongStatus(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public void moveToNextSong() {
        currentSong = findNextLikedSong();
    }
}