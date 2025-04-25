package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

//Manages the application window and action events within
//Connects the UI events to the controller actions and updates the UI in response
public class UserControls extends JFrame {
    private final MusicPlayerController controller; // Controls the music player functionality
    private final UserInterface ui; // Reference to the UI components

    //Constructor that initializes the controls window with controller and timer
    public UserControls(MusicPlayerController controller, TimerService timerService) {
        super("Music Player Controls");//Set window title
        this.controller = controller;

        // Configure window visibility
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        // Initialize UI first
        this.ui = new UserInterface(this);

        // Create the listener
        // Play the alarm first
        // Update the UI
        // If music was playing, update the play/pause button
        // Force UI update

        // Create timer listener implementation
        TimerService.TimerListener listener = new TimerService.TimerListener() {
            //Update UI when timer ticks
            @Override
            public void onTimerTick(int remainingSeconds) {
                String formattedTime = formatTime(remainingSeconds);
                System.out.println("UI updating to: " + formattedTime);

                // Update UI on Event Dispatch Thread for thread safety
                SwingUtilities.invokeLater(() -> {
                    ui.getTimerLabel().setText(formattedTime);
                    ui.getTimerLabel().revalidate();
                    ui.getTimerLabel().repaint();
                    getContentPane().repaint();
                    System.out.println("Label verified: " + ui.getTimerLabel().getText());
                });
            }

            //Handles timer completion by updating UI and notifying controller
            @Override
            public void onTimerComplete() {
                System.out.println("On Timer worked!");
                // Notify controller first
                controller.handleTimerCompletion();

                // Update the UI
                ui.getTimerLabel().setText("Time's up!");
                ui.getTimerToggleButton().setText("Start Timer");

                // Update play button if music stopped
                if (!controller.isPlaying()) {
                    ui.getPlayPauseButton().setText("Play Music");
                }

                // Force UI update
                ui.getTimerLabel().revalidate();
                ui.getTimerLabel().repaint();

            }
        };

        //Register the listener with timer service
        System.out.println("Registering listener with TimerService");
        timerService.setListener(listener);

        // Connect buttons to their event handlers
        setupEventListeners();
    }

    //Connects UI buttons to their respective action handlers
    private void setupEventListeners() {
        ui.getPlayPauseButton().addActionListener(this::handlePlayPause);
        ui.getTimerToggleButton().addActionListener(this::handleTimerToggle);
        ui.getLoopButton().addActionListener(this::handleLoop);
        ui.getDislikeButton().addActionListener(this::handleDislike);
        ui.getNextButton().addActionListener(this::handleNext);
        ui.getResetButton().addActionListener(this::handleReset);
        ui.getQuitButton().addActionListener(this::handleQuit);
    }

    //Handles play/pause button clicks
    //Toggle music playback then update button text
    private void handlePlayPause(ActionEvent e) {
        controller.togglePlayPause();
        ui.getPlayPauseButton().setText(controller.isPlaying() ? "Pause Music" : "Play Music");
    }
    //Handles timer button clicks
    //Either start a new timer or stop the current one
    private void handleTimerToggle(ActionEvent e) {
        if (!controller.isTimerRunning()) {
            try {
                // Parse user input for minutes
                int minutes = Integer.parseInt(ui.getTimeInput().getText());
                controller.startTimer(minutes);
                ui.getTimerToggleButton().setText("Stop Timer");
            } catch (NumberFormatException ex) {
                ui.getTimerLabel().setText("Invalid input!");
            }
        } else {
            // Stop running timer
            controller.stopTimer();
            ui.getTimerToggleButton().setText("Start Timer");
            ui.getTimerLabel().setText("Timer Stopped");
        }
    }

    //Handles loop button clicks
    //Toggle song looping and update button text
    private void handleLoop(ActionEvent e) {
        controller.toggleLoop();
        ui.getLoopButton().setText(controller.isLoopEnabled() ? "Undo Loop" : "Loop Music");
    }

    //Handles dislike button clicks
    //Mark current song as disliked and show temporary confirmation
    private void handleDislike(ActionEvent e) {
        try {
            ui.getDislikeButton().setText("Disliked!");
            controller.dislikeCurrentSong();

            // Reset button text after (2 seconds or 2000 milliseconds)
            Timer messageTimer = new Timer(2000, _ -> ui.getDislikeButton().setText("Dislike Song"));
            messageTimer.setRepeats(false);
            messageTimer.start();
        } catch (Exception ex) {
            showError("Error disliking song: " + ex.getMessage());
        }
    }

    //Handles next button clicks
    //Skip to the next song
    private void handleNext(ActionEvent e) {
        try {
            controller.playNextSong();
        } catch (Exception ex) {
            showError("Error playing next song: " + ex.getMessage());
        }
    }

    //Handles reset button clicks
    //Restart the current song from the beginning
    private void handleReset(ActionEvent e) {
        controller.resetCurrentSong();
    }

    //Handles quit button clicks
    //Shut down the player and exits the application
    private void handleQuit(ActionEvent e) {
        controller.shutdown();
        dispose();
        System.exit(0);
    }

    //Formats seconds into MM:SS display format
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    //Displays error messages in the UI and console
    private void showError(String message) {
        System.err.println(message);
        ui.getTimerLabel().setText(message);
    }
}
