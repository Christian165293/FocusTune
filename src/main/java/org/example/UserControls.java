package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UserControls extends JFrame {
    private final AudioPlaybackController playbackController;
    private final SongQueueNavigator queueNavigator;
    private final LoopManager loopManager;
    private final AlarmPlayer alarmPlayer;
    private final MusicRatings musicRatings;
    private final TimerService timerService;
    private final PlayerStateManager stateManager;
    private final UserInterface ui;

    public UserControls(AudioPlaybackController playbackController,
                        SongQueueNavigator queueNavigator,
                        LoopManager loopManager,
                        AlarmPlayer alarmPlayer,
                        MusicRatings musicRatings) {
        super("Music Player Controls");
        this.playbackController = playbackController;
        this.queueNavigator = queueNavigator;
        this.loopManager = loopManager;
        this.alarmPlayer = alarmPlayer;
        this.musicRatings = musicRatings;
        this.stateManager = new PlayerStateManager();

        // Initialize TimerService with listeners
        this.timerService = new TimerService(new TimerService.TimerListener() {
            @Override
            public void onTimerTick(int remainingSeconds) {
                ui.getTimerLabel().setText(formatTime(remainingSeconds));
            }

            @Override
            public void onTimerComplete() {
                handleTimerCompletion();
            }
        });

        this.ui = new UserInterface(this);
        setupEventListeners();
    }

    private void setupEventListeners() {
        ui.getPlayPauseButton().addActionListener(this::handlePlayPause);
        ui.getTimerToggleButton().addActionListener(this::handleTimerToggle);
        ui.getLoopButton().addActionListener(this::handleLoop);
        ui.getDislikeButton().addActionListener(this::handleDislike);
        ui.getNextButton().addActionListener(this::handleNext);
        ui.getResetButton().addActionListener(this::handleReset);
        ui.getQuitButton().addActionListener(this::handleQuit);
    }

    private void handlePlayPause(ActionEvent e) {
        if (stateManager.isPlaying()) {
            playbackController.stopPlayback();
            ui.getPlayPauseButton().setText("Play Music");
        } else {
            playbackController.startPlayback();
            ui.getPlayPauseButton().setText("Pause Music");
        }
        stateManager.setPlaying(!stateManager.isPlaying());
    }

    private void handleTimerToggle(ActionEvent e) {
        if (!timerService.isRunning()) {
            try {
                int minutes = Integer.parseInt(ui.getTimeInput().getText());
                timerService.startTimer(minutes);
                ui.getTimerToggleButton().setText("Stop Timer");
            } catch (NumberFormatException ex) {
                ui.getTimerLabel().setText("Invalid input!");
            }
        } else {
            timerService.stopTimer();
            ui.getTimerToggleButton().setText("Start Timer");
            ui.getTimerLabel().setText("Timer Stopped");
        }
    }

    private void handleLoop(ActionEvent e) {
        if (loopManager.isLoopEnabled()) {
            loopManager.disableLoop();
            ui.getLoopButton().setText("Loop Music");
        } else {
            loopManager.enableLoop();
            ui.getLoopButton().setText("Undo Loop");
        }
    }

    private void handleDislike(ActionEvent e) {
        String currentSong = queueNavigator.getCurrentSong();
        if (currentSong != null) {
            musicRatings.dislikeSong(currentSong);
            ui.getDislikeButton().setText("Disliked!");

            if (musicRatings.checkForLikedRatings()) {
                musicRatings.resetAllRatings();
                ui.getTimerLabel().setText("Reset disliked songs");

                Timer messageTimer = new Timer(2000, _ -> {
                    try {
                        playNextSong();
                        ui.getTimerLabel().setText("Playing next song");
                    } catch (Exception ex) {
                        showError("Error moving to next song: " + ex.getMessage());
                    }
                });
                messageTimer.setRepeats(false);
                messageTimer.start();
            } else {
                playNextSong();
            }

            new Timer(2000, _ -> ui.getDislikeButton().setText("Dislike Song")).start();
        }
    }

    private void handleNext(ActionEvent e) {
        playNextSong();
    }

    private void playNextSong() {
        try {
            queueNavigator.moveToNextSong();
            playbackController.loadAudio(queueNavigator.getCurrentSong());
            if (stateManager.isPlaying()) {
                playbackController.startPlayback();
                ui.getPlayPauseButton().setText("Pause Music");
            }
        } catch (Exception ex) {
            showError("Error moving to next song: " + ex.getMessage());
        }
    }

    private void handleReset(ActionEvent e) {
        playbackController.reset();
        if (stateManager.isPlaying()) {
            playbackController.startPlayback();
        }
    }

    private void handleQuit(ActionEvent e) {
        playbackController.close();
        dispose();
        System.exit(0);
    }

    private void handleTimerCompletion() {
        ui.getTimerLabel().setText("Time's up!");
        ui.getTimerToggleButton().setText("Start Timer");
        playbackController.stopPlayback();
        alarmPlayer.playAlarm();

        if (stateManager.isPlaying()) {
            stateManager.setPlaying(false);
            ui.getPlayPauseButton().setText("Play Music");
        }
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void showError(String message) {
        System.err.println(message);
        ui.getTimerLabel().setText(message);
    }
}
