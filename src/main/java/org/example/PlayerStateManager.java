package org.example;

//Tracks whether music is currently playing
public class PlayerStateManager {
    private boolean isPlaying = false;

    //returns true if the media player is currently playing a file
    public boolean isPlaying() {
        return isPlaying;
    }

    //set the player true for playing and false for stopped/paused
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
