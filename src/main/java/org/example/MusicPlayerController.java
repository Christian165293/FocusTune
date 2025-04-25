package org.example;

//Central controller that coordinated the music playback, timer functionality
//and other important features of the music player
public class MusicPlayerController {
    private final InterfacePlaybackController playbackController;//Controls audio playback
    private final InterfaceQueueNavigator queueNavigator;//Navigates the song queue
    private final LoopManager loopManager;//Manages loop functionality
    private final AlarmPlayer alarmPlayer;//Manages the alarm when the timer completes
    private final InterfaceMusicRatings musicRatings;//Manages the music dislikes/likes
    private final TimerService timerService;//Controls the Timer's functionality
    private final PlayerStateManager stateManager;//tracks if media player is paused or playing

    //constructor that initializes controller with all the required dependencies
    public MusicPlayerController(
            InterfacePlaybackController playbackController,
            InterfaceQueueNavigator queueNavigator,
            LoopManager loopManager,
            AlarmPlayer alarmPlayer,
            InterfaceMusicRatings musicRatings,
            TimerService timerService) {

        this.playbackController = playbackController;
        this.queueNavigator = queueNavigator;
        this.loopManager = loopManager;
        this.alarmPlayer = alarmPlayer;
        this.musicRatings = musicRatings;
        this.timerService = timerService;
        this.stateManager = new PlayerStateManager();
    }

    //toggles if player is playing or paused
    public void togglePlayPause() {
        if (stateManager.isPlaying()) {
            playbackController.stopPlayback();
        } else {
            playbackController.startPlayback();
        }
        stateManager.setPlaying(!stateManager.isPlaying());
    }

    //returns true if the media player is playing
    public boolean isPlaying() {
        return stateManager.isPlaying();
    }

    //starts the timer with the users specified duration
    public void startTimer(int minutes) {
        timerService.startTimer(minutes);
    }

    //stops the timer
    public void stopTimer() {
        timerService.stopTimer();
    }

    //returns true if the timer is running
    public boolean isTimerRunning() {
        return timerService.isRunning();
    }

    //toggles if the loop function is on or off
    public void toggleLoop() {
        if (loopManager.isLoopEnabled()) {
            loopManager.disableLoop();
            System.out.println("Loop disabled");
        } else {
            loopManager.enableLoop();
            System.out.println("Loop enabled");
        }
    }

    //returns true if loop is enabled
    public boolean isLoopEnabled() {
        return loopManager.isLoopEnabled();
    }

    //mark the current song as disliked so that it will be skipped during the future playlist iterations
    public void dislikeCurrentSong() {
        String currentSong = queueNavigator.getCurrentSong();
        if (currentSong != null) {
            //marking current song as disliked
            musicRatings.dislikeSong(currentSong);
            //resets the user ratings if all songs are disliked
            if (musicRatings.checkForLikedRatings()) {
                musicRatings.resetAllRatings();
            }
            //begin next song
            playNextSong();
        }
    }

    //moves to next song in queue to be played
    public void playNextSong() {
        try {
            queueNavigator.moveToNextSong();
            playbackController.loadAudio(queueNavigator.getCurrentSong());
            //if the audio player is playing then restart time of next song to beginning
            if (stateManager.isPlaying()) {
                playbackController.startPlayback();
            }
        } catch (Exception ex) {
            System.err.println("Error moving to next song: " + ex.getMessage());
            throw new RuntimeException("Failed to play next song", ex);
        }
    }

    //resets the song to the beginning
    public void resetCurrentSong() {
        playbackController.reset();
        //if song was playing then make the audio continue to still play after reset
        if (stateManager.isPlaying()) {
            playbackController.startPlayback();
        }
    }

    //when the timer reaches zero from the users duration then it will play alarm
    public void handleTimerCompletion() {
        playbackController.stopPlayback();
        alarmPlayer.playAlarm();  // This should play the alarm sound
        //ensure the timer does not automatically start counting down
        if (stateManager.isPlaying()) {
            stateManager.setPlaying(false);
        }
    }

    //cleans up resources when shutting down the application
    public void shutdown() {
        playbackController.close();
    }
}
