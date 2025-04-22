package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UserControls extends JFrame{
    private final AudioPlayer audioPlayer;
    private final UserInterface ui;
    private javax.swing.Timer timer;
    private int remainingSeconds;
    private boolean isPlaying = false;
    private boolean isTimerRunning = false;

    public UserControls(AudioPlayer audioPlayer) {
        super("Music Player Controls");
        this.audioPlayer = audioPlayer;
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
    }

    private void handlePlayPause(ActionEvent e) {
        if (isPlaying) {
            audioPlayer.stopPlayback();
            ui.getPlayPauseButton().setText("Play Music");
        } else {
            audioPlayer.startPlayback();
            ui.getPlayPauseButton().setText("Pause Music");
        }
        isPlaying = !isPlaying;
    }

    private void handleTimerToggle(ActionEvent e) {
        if (!isTimerRunning) {
            try {
                int minutes = Integer.parseInt(ui.getTimeInput().getText());
                remainingSeconds = minutes * 60;
                ui.getTimerLabel().setText(formatTime(remainingSeconds));

                if (timer != null) {
                    timer.stop();
                }

                timer = new javax.swing.Timer(1000, _ -> {
                    remainingSeconds--;
                    if (remainingSeconds >= 0) {
                        ui.getTimerLabel().setText(formatTime(remainingSeconds));
                    }
                    if (remainingSeconds <= 0) {
                        ui.getTimerLabel().setText("00:00");
                        timer.stop();
                        isTimerRunning = false;
                        ui.getTimerLabel().setText("Time's up!");
                        ui.getTimerToggleButton().setText("Start Timer");
                        audioPlayer.stopPlayback();
                        audioPlayer.playAlarm();
                        if (isPlaying) {
                            isPlaying = false;
                            ui.getPlayPauseButton().setText("Play Music");
                        }
                    }
                });
                timer.start();
                isTimerRunning = true;
                ui.getTimerToggleButton().setText("Stop Timer");
            } catch (NumberFormatException ex) {
                ui.getTimerLabel().setText("Invalid input!");
            }
        } else {
            if (timer != null) {
                timer.stop();
            }
            isTimerRunning = false;
            ui.getTimerToggleButton().setText("Start Timer");
            ui.getTimerLabel().setText("Timer Stopped");
        }
    }

    private void handleLoop(ActionEvent e) {
        if (audioPlayer.isLoopEnabled()) {
            audioPlayer.disableLoop();
            ui.getLoopButton().setText("Loop Music");
        } else {
            audioPlayer.enableLoop();
            ui.getLoopButton().setText("Undo Loop");
        }
    }

    private void handleDislike(ActionEvent e) {
        String currentSong = audioPlayer.getCurrentSong();
        if (currentSong != null) {
            audioPlayer.dislikeCurrentSong();
            ui.getDislikeButton().setText("Disliked!");

            // Check if we need to reset ratings
            if (!audioPlayer.checkIfRatingsFalse()) {
                audioPlayer.getMusicRatings().resetAllRatings();
                ui.getTimerLabel().setText("Reset disliked songs");
                Timer messageTimer = new Timer(2000, _ -> {
                    try {
                        audioPlayer.nextSong();
                        ui.getTimerLabel().setText("Playing next song");
                    } catch (Exception ex) {
                        System.err.println("Error moving to next song: " + ex.getMessage());
                    }
                });
                messageTimer.setRepeats(false);
                messageTimer.start();
            } else {
                try {
                    audioPlayer.nextSong();
                } catch (Exception ex) {
                    System.err.println("Error moving to next song: " + ex.getMessage());
                }
            }

            // Reset dislike button text after 2 seconds
            new Timer(2000, _ -> ui.getDislikeButton().setText("Dislike Song")).start();
        }
    }

    private void handleNext(ActionEvent e) {
        try {
            audioPlayer.nextSong();
            if (isPlaying) {
                ui.getPlayPauseButton().setText("Pause Music");
            }
        } catch (Exception ex) {
            System.err.println("Error moving to next song: " + ex.getMessage());
        }
    }

    private void handleReset(ActionEvent e) {
        audioPlayer.reset();
        if (isPlaying) {
            audioPlayer.startPlayback();
        }
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
