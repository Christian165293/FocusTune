package org.example;

public class Application {
    private final AudioPlayer audioPlayer;
    private final UserControls controls;

    public Application(MusicQueue musicQueue, MusicRatings musicRatings) {
        this.audioPlayer = new AudioPlayer(musicQueue, musicRatings);
        this.controls = new UserControls(audioPlayer);
    }

    public void run() {
        try {
            audioPlayer.loadAudio();
            controls.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error initializing player: " + e.getMessage());
        }
    }
}
