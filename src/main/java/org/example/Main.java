package org.example;

import java.nio.file.Paths;

//Program entry point that initializes all components and starts the application
public class Main {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();

        if (config.isMusicQueueEmpty()) {
            System.out.println("Input MP3 files folder is currently empty");
            System.out.println("Please add MP3 files and restart the program");
            return;
        }

        try {
            // Initialize application with configuration
            Application app = config.createApplication();
            app.run();
        } catch (Exception e) {
            System.err.println("Error initializing application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Configuration class that handles dependency injection
class ApplicationConfig {
    private final InterfaceMusicQueue musicQueue;
    private final InterfacePlaybackController playbackController;
    private final LoopManager loopManager;
    private final TimerService timerService;
    private final MusicPlayerController musicPlayerController;

    public ApplicationConfig() {
        // Initialize components with dependencies
        this.musicQueue = new MusicQueue();
        InterfaceMusicRatings musicRatings = new MusicRatings();

        // Initialize playlist loader
        PlaylistLoader playlistLoader = new PlaylistLoader(
                Paths.get(System.getProperty("user.dir"), "src/InputMP3FilesHere"),
                new FileValidator()
        );
        playlistLoader.loadPlaylist(musicQueue, musicRatings);

        // Initialize audio components - FIX: Pass instance of MP3FileReader, not the class itself
        this.playbackController = new AudioPlaybackController(new AudioFileConverter.MP3FileReader());
        this.loopManager = new LoopManager();
        AlarmPlayer alarmPlayer = new AlarmPlayer();

        // Create the selection strategy and queue navigator
        InterfaceSongSelection selectionStrategy = new SongSelection(loopManager);
        InterfaceQueueNavigator queueNavigator = new SongQueueNavigator(musicQueue, musicRatings, selectionStrategy);

        // Create timer service with placeholder listeners (will be replaced in UserControls)
        this.timerService = new TimerService(null);

        // Create controller with all dependencies
        this.musicPlayerController = new MusicPlayerController(
                playbackController,
                queueNavigator,
                loopManager,
                alarmPlayer,
                musicRatings,
                timerService
        );

        // Set up the playback controller with queue navigator
        playbackController.setOnPlaybackComplete(() -> {
            System.out.println("Playback complete callback triggered");
            if (!loopManager.isLoopEnabled()) {
                System.out.println("Loop not enabled, moving to next song");
                try {
                    musicPlayerController.playNextSong();
                } catch (Exception e) {
                    System.err.println("Error moving to next song: " + e.getMessage());
                }
            } else {
                System.out.println("Loop enabled, replaying current song");
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
            throw new RuntimeException("Failed to initialize application", e);
        }
    }

    public boolean isMusicQueueEmpty() {
        return musicQueue.isEmpty();
    }
    public Application createApplication() {
        UserControls userControls = new UserControls(musicPlayerController, timerService);
        return new Application(userControls);
    }
}
