package org.example;

public class MusicPlayerController {
    private final InterfacePlaybackController playbackController;
    private final InterfaceQueueNavigator queueNavigator;
    private final LoopManager loopManager;
    private final AlarmPlayer alarmPlayer;
    private final InterfaceMusicRatings musicRatings;
    private final TimerService timerService;
    private final PlayerStateManager stateManager;

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

    public void togglePlayPause() {
        if (stateManager.isPlaying()) {
            playbackController.stopPlayback();
        } else {
            playbackController.startPlayback();
        }
        stateManager.setPlaying(!stateManager.isPlaying());
    }

    public boolean isPlaying() {
        return stateManager.isPlaying();
    }

    public void startTimer(int minutes) {
        timerService.startTimer(minutes);
    }

    public void stopTimer() {
        timerService.stopTimer();
    }

    public boolean isTimerRunning() {
        return timerService.isRunning();
    }

    public void toggleLoop() {
        if (loopManager.isLoopEnabled()) {
            loopManager.disableLoop();
            System.out.println("Loop disabled");
        } else {
            loopManager.enableLoop();
            System.out.println("Loop enabled");
        }
    }

    public boolean isLoopEnabled() {
        return loopManager.isLoopEnabled();
    }

    public void dislikeCurrentSong() {
        String currentSong = queueNavigator.getCurrentSong();
        if (currentSong != null) {
            musicRatings.dislikeSong(currentSong);

            if (musicRatings.checkForLikedRatings()) {
                musicRatings.resetAllRatings();
            }

            playNextSong();
        }
    }

    public void playNextSong() {
        try {
            queueNavigator.moveToNextSong();
            playbackController.loadAudio(queueNavigator.getCurrentSong());
            if (stateManager.isPlaying()) {
                playbackController.startPlayback();
            }
        } catch (Exception ex) {
            System.err.println("Error moving to next song: " + ex.getMessage());
            throw new RuntimeException("Failed to play next song", ex);
        }
    }

    public void resetCurrentSong() {
        playbackController.reset();
        if (stateManager.isPlaying()) {
            playbackController.startPlayback();
        }
    }

    public void handleTimerCompletion() {
        playbackController.stopPlayback();
        alarmPlayer.playAlarm();  // This should play the alarm sound

        if (stateManager.isPlaying()) {
            stateManager.setPlaying(false);
        }
    }

    public void shutdown() {
        playbackController.close();
    }
}
