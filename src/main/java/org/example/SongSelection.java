package org.example;

public class SongSelection implements InterfaceSongSelection{
    private final LoopManager loopManager;

    public SongSelection(LoopManager loopManager) {
        this.loopManager = loopManager;
    }

    @Override
    public String selectNextSong(InterfaceMusicQueue queue, InterfaceMusicRatings ratings) {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }

        if (ratings.checkForLikedRatings()) {
            System.out.println("All songs disliked, resetting ratings");
            ratings.resetAllRatings();
        }

        if(!loopManager.isLoopEnabled()) {
            String oldSong = queue.peek();
            queue.remove();
            queue.add(oldSong);
            System.out.println("Moved current song to end: " + oldSong);
        }

        String candidate = queue.peek();
        System.out.println("Next candidate: " + candidate);

        if (ratings.getSongStatus(candidate)) {
            System.out.println("Found liked song: " + candidate);
            return candidate;
        }

        int originalSize = queue.getSize();
        for (int i = 0; i < originalSize - 1; i++) { // -1 because we already moved one song
            String currentHead = queue.peek();
            queue.remove();
            queue.add(currentHead);

            candidate = queue.peek();
            System.out.println("Checking candidate: " + candidate);

            if (ratings.getSongStatus(candidate)) {
                System.out.println("Found liked song: " + candidate);
                return candidate;
            }
        }

        // If all songs are disliked (shouldn't happen due to resetAllRatings above)
        if (originalSize > 0) {
            System.out.println("No liked songs found, using first song");
            return queue.peek();
        }

        System.out.println("No songs in queue");
        return null;
    }
}
