package org.example;

//Manages navigation through the MusicQueue by tracking current song and coordinating song transitions
public class SongQueueNavigator implements InterfaceQueueNavigator{
    private final InterfaceMusicQueue musicQueue;//The queue of songs
    private final InterfaceMusicRatings musicRatings;//The song rating information
    private final InterfaceSongSelection songSelection;//The song selection logic
    private String currentSong;//path to the song currently playing

    //Constructor that initializes the queue navigator with required components
    public SongQueueNavigator(InterfaceMusicQueue musicQueue, InterfaceMusicRatings musicRatings, InterfaceSongSelection songSelection) {
        this.musicQueue = musicQueue;
        this.musicRatings = musicRatings;
        this.songSelection = songSelection;
        // Initialize currentSong with first song in queue
        this.currentSong = musicQueue.isEmpty() ? null : musicQueue.peek();
    }
    //get file path to the current song
    @Override
    public String getCurrentSong() {
        return currentSong;
    }
    //Moves to the next song based upon the song selection algorithm and updates current song
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