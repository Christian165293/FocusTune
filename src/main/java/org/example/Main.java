package org.example;

//Program entry point that initializes all components and starts the application
public class Main {
    public static void main(String[] args) {
        // Initialize components
        MusicQueue musicQueue = new MusicQueue();
        MusicRatings musicRatings = new MusicRatings();
        InitializePlaylist.fillMusicQueue(musicQueue, musicRatings);

        if (musicQueue.isEmpty()) {
            System.out.println("Input waves files folder is currently empty");
            System.out.println("please add wave files and reset program");
            return;
        }

        // Initialize specialized components
        AudioPlaybackController playbackController = new AudioPlaybackController();
        SongQueueNavigator queueNavigator = new SongQueueNavigator(musicQueue, musicRatings);
        LoopManager loopManager = new LoopManager();
        AlarmPlayer alarmPlayer = new AlarmPlayer();

        // Set up the playback controller with queue navigator
        playbackController.setOnPlaybackComplete(() -> {
            if (!loopManager.isLoopEnabled()) {
                queueNavigator.moveToNextSong();
                try {
                    playbackController.loadAudio(queueNavigator.getCurrentSong());
                    playbackController.startPlayback();
                } catch (Exception e) {
                    System.err.println("Error moving to next song: " + e.getMessage());
                }
            } else {
                playbackController.reset();
                playbackController.startPlayback();
            }
        });

        // Load initial song
        try {
            // Initialize queueNavigator's currentSong
            queueNavigator.moveToNextSong();
            playbackController.loadAudio(queueNavigator.getCurrentSong());
        } catch (Exception e) {
            System.err.println("Error loading initial song: " + e.getMessage());
            return;
        }

        // Create and run application through Application class
        Application app = new Application(
                playbackController,
                queueNavigator,
                loopManager,
                alarmPlayer,
                musicRatings
        );
        app.run();
    }
}
