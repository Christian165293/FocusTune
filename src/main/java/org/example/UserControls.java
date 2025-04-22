package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

//UserControls handles action events
public class UserControls extends JFrame {
    private final MusicPlayerController controller;
    private final UserInterface ui;

    public UserControls(MusicPlayerController controller, TimerService timerService) {
        super("Music Player Controls");
        this.controller = controller;

        // Make window visible for debugging
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        // Initialize UI first
        this.ui = new UserInterface(this);

        // Create the listener
        // Play the alarm first
        // Update the UI
        // If music was playing, update the play/pause button
        // Force UI update
        TimerService.TimerListener listener = new TimerService.TimerListener() {
            @Override
            public void onTimerTick(int remainingSeconds) {
                String formattedTime = formatTime(remainingSeconds);
                System.out.println("UI updating to: " + formattedTime);

                SwingUtilities.invokeLater(() -> {
                    ui.getTimerLabel().setText(formattedTime);
                    ui.getTimerLabel().revalidate();
                    ui.getTimerLabel().repaint();
                    getContentPane().repaint();
                    System.out.println("Label verified: " + ui.getTimerLabel().getText());
                });
            }

            @Override
            public void onTimerComplete() {
                System.out.println("On Timer worked!");
                // Play the alarm first
                controller.handleTimerCompletion();

                // Update the UI
                ui.getTimerLabel().setText("Time's up!");
                ui.getTimerToggleButton().setText("Start Timer");

                // If music was playing, update the play/pause button
                if (!controller.isPlaying()) {
                    ui.getPlayPauseButton().setText("Play Music");
                }

                // Force UI update
                ui.getTimerLabel().revalidate();
                ui.getTimerLabel().repaint();

            }
        };

        // Register the listener
        System.out.println("Registering listener with TimerService");
        timerService.setListener(listener);

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
        controller.togglePlayPause();
        ui.getPlayPauseButton().setText(controller.isPlaying() ? "Pause Music" : "Play Music");
    }

    private void handleTimerToggle(ActionEvent e) {
        if (!controller.isTimerRunning()) {
            try {
                int minutes = Integer.parseInt(ui.getTimeInput().getText());
                controller.startTimer(minutes);
                ui.getTimerToggleButton().setText("Stop Timer");
            } catch (NumberFormatException ex) {
                ui.getTimerLabel().setText("Invalid input!");
            }
        } else {
            controller.stopTimer();
            ui.getTimerToggleButton().setText("Start Timer");
            ui.getTimerLabel().setText("Timer Stopped");
        }
    }

    private void handleLoop(ActionEvent e) {
        controller.toggleLoop();
        ui.getLoopButton().setText(controller.isLoopEnabled() ? "Undo Loop" : "Loop Music");
    }

    private void handleDislike(ActionEvent e) {
        try {
            ui.getDislikeButton().setText("Disliked!");
            controller.dislikeCurrentSong();

            Timer messageTimer = new Timer(2000, _ -> ui.getDislikeButton().setText("Dislike Song"));
            messageTimer.setRepeats(false);
            messageTimer.start();
        } catch (Exception ex) {
            showError("Error disliking song: " + ex.getMessage());
        }
    }

    private void handleNext(ActionEvent e) {
        try {
            controller.playNextSong();
        } catch (Exception ex) {
            showError("Error playing next song: " + ex.getMessage());
        }
    }

    private void handleReset(ActionEvent e) {
        controller.resetCurrentSong();
    }

    private void handleQuit(ActionEvent e) {
        controller.shutdown();
        dispose();
        System.exit(0);
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
