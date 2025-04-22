package org.example;

//Tracks whether music is currently playing
public class PlayerStateManager {
    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
