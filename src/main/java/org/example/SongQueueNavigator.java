package org.example;

//Manages navigation through the MusicQueue
public class SongQueueNavigator {
    private final MusicQueue musicQueue;
    private final MusicRatings musicRatings;
    private final LoopManager loopManager;
    private String currentSong;


    public SongQueueNavigator(MusicQueue musicQueue, MusicRatings musicRatings,LoopManager loopManager) {
        this.musicQueue = musicQueue;
        this.musicRatings = musicRatings;
        this.loopManager = loopManager;
        // Initialize currentSong
        this.currentSong = musicQueue.isEmpty() ? null : musicQueue.peek();
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public String findNextLikedSong() {
        if (musicQueue.isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }

        if (musicRatings.checkForLikedRatings()) {
            System.out.println("All songs disliked, resetting ratings");
            musicRatings.resetAllRatings();
        }
        if(!loopManager.isLoopEnabled()) {
            String oldSong = musicQueue.peek();
            musicQueue.remove();
            musicQueue.add(oldSong);
            System.out.println("Moved current song to end: " + oldSong);
        }

        String candidate = musicQueue.peek();
        System.out.println("Next candidate: " + candidate);

        if (musicRatings.getSongStatus(candidate)) {
            System.out.println("Found liked song: " + candidate);
            return candidate;
        }

        int originalSize = musicQueue.getSize();
        for (int i = 0; i < originalSize - 1; i++) { // -1 because we already moved one song
            String currentHead = musicQueue.peek();
            musicQueue.remove();
            musicQueue.add(currentHead);

            candidate = musicQueue.peek();
            System.out.println("Checking candidate: " + candidate);

            if (musicRatings.getSongStatus(candidate)) {
                System.out.println("Found liked song: " + candidate);
                return candidate;
            }
        }

        // If all songs are disliked (shouldn't happen due to resetAllRatings above)
        if (originalSize > 0) {
            System.out.println("No liked songs found, using first song");
            return musicQueue.peek();
        }

        System.out.println("No songs in queue");
        return null;
    }

    public void moveToNextSong() {
        String nextSong = findNextLikedSong();
        if (nextSong != null) {
            currentSong = nextSong;
            System.out.println("Moving to next song: " + currentSong);
        } else {
            System.err.println("Error: Could not find a valid next song");
        }
    }
}