package org.example;

import java.nio.file.Paths;

//Program entry point that initializes all components and starts the application
public class Main {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();

        //exit if no music files available
        if (config.isMusicQueueEmpty()) {
            System.out.println("Input MP3 files folder is currently empty");
            System.out.println("Please add MP3 files and restart the program");
            return;
        }

        try {
            // Initialize application and try to run
            Application app = config.createApplication();
            app.run();
        } catch (Exception e) {
            System.err.println("Error initializing application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Configuration class that handles dependency injection for all components
class ApplicationConfig {
    private final InterfaceMusicQueue musicQueue;
    private final InterfacePlaybackController playbackController;
    private final LoopManager loopManager;
    private final TimerService timerService;
    private final MusicPlayerController musicPlayerController;

    //constructor to initialize all components with dependencies
    public ApplicationConfig() {
//initialize the core data structures
        this.musicQueue = new MusicQueue();
        InterfaceMusicRatings musicRatings = new MusicRatings();

        // Initialize playlist loader to read the music files
        PlaylistLoader playlistLoader = new PlaylistLoader(
                Paths.get(System.getProperty("user.dir"), "src/InputMP3FilesHere"),
                new FileValidator()
        );
        playlistLoader.loadPlaylist(musicQueue, musicRatings);

        // Initialize audio components
        this.playbackController = new AudioPlaybackController(new AudioFileConverter.MP3FileReader());
        this.loopManager = new LoopManager();
        AlarmPlayer alarmPlayer = new AlarmPlayer();

        // Create the selection logic and queue navigator
        InterfaceSongSelection selectionStrategy = new SongSelection(loopManager);
        InterfaceQueueNavigator queueNavigator = new SongQueueNavigator(musicQueue, musicRatings, selectionStrategy);

        // Create timer service with placeholder listeners to be replaced in UserControls
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
                //if the loop is disabled then move to next when the current song finishes
                System.out.println("Loop not enabled, moving to next song");
                try {
                    musicPlayerController.playNextSong();
                } catch (Exception e) {
                    System.err.println("Error moving to next song: " + e.getMessage());
                }
            } else {
                //if the loop is enabled then replay the current song
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

    //return true if the music queue is empty
    public boolean isMusicQueueEmpty() {
        return musicQueue.isEmpty();
    }

    //creates the Application with the configured dependencies
    public Application createApplication() {
        UserControls userControls = new UserControls(musicPlayerController, timerService);
        return new Application(userControls);
    }
}
