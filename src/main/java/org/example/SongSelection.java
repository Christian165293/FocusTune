package org.example;

//Algorithm which determines next song to play based upon likes/dislikes
public class SongSelection implements InterfaceSongSelection{
    private final LoopManager loopManager;//Manages whether to loop current song
    //Constructor which initializes the loop manager
    public SongSelection(LoopManager loopManager) {
        this.loopManager = loopManager;
    }
    //Select next song based on the queue,rating, and loop status
    @Override
    public String selectNextSong(InterfaceMusicQueue queue, InterfaceMusicRatings ratings) {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        //If all the songs are disliked then reset the ratings
        if (ratings.checkForLikedRatings()) {
            System.out.println("All songs disliked, resetting ratings");
            ratings.resetAllRatings();
        }
        //If the loop is disabled then move the current song to end of the queue
        //and remove the current song from the head
        if(!loopManager.isLoopEnabled()) {
            String oldSong = queue.peek();
            queue.remove();
            queue.add(oldSong);
            System.out.println("Moved current song to end: " + oldSong);
        }
        //Checking if the song in the front of the queue is liked
        String candidate = queue.peek();
        System.out.println("Next candidate: " + candidate);

        if (ratings.getSongStatus(candidate)) {
            System.out.println("Found liked song: " + candidate);
            return candidate;
        }
        //If the current song is disliked then now search through the queue for a liked song
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
