package org.example;

//Manages navigation through the MusicQueue
public class SongQueueNavigator implements InterfaceQueueNavigator{
    private final InterfaceMusicQueue musicQueue;
    private final InterfaceMusicRatings musicRatings;
    private final InterfaceSongSelection songSelection;
    private String currentSong;

    public SongQueueNavigator(InterfaceMusicQueue musicQueue, InterfaceMusicRatings musicRatings, InterfaceSongSelection songSelection) {
        this.musicQueue = musicQueue;
        this.musicRatings = musicRatings;
        this.songSelection = songSelection;
        // Initialize currentSong
        this.currentSong = musicQueue.isEmpty() ? null : musicQueue.peek();
    }

    @Override
    public String getCurrentSong() {
        return currentSong;
    }

    @Override
    public void moveToNextSong() {
        String nextSong = songSelection.selectNextSong(musicQueue, musicRatings);
        if (nextSong != null) {
            currentSong = nextSong;
            System.out.println("Moving to next song: " + currentSong);
        } else {
            System.err.println("Error: Could not find a valid next song");
        }
    }
}