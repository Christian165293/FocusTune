package org.example;

import javax.swing.*;
import java.awt.*;

public class UserInterface {
    private final JLabel timerLabel;
    private final JTextField timeInput;
    private final JButton playPauseButton;
    private final JButton timerToggleButton;
    private final JButton loopButton;
    private final JButton dislikeButton;
    private final JButton nextButton;
    private final JButton resetButton;

    public UserInterface(JFrame sharedFrame) {
        // Configure the shared frame
        sharedFrame.setLayout(new GridLayout(5, 2, 5, 5));
        sharedFrame.setSize(400, 250);
        sharedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Timer input components
        sharedFrame.add(new JLabel("Timer (minutes):", SwingConstants.CENTER));
        timeInput = new JTextField(5);
        sharedFrame.add(timeInput);

        // Main control buttons
        playPauseButton = new JButton("Play Music");
        sharedFrame.add(playPauseButton);

        timerToggleButton = new JButton("Start Timer");
        sharedFrame.add(timerToggleButton);

        // Song control buttons
        loopButton = new JButton("Loop Music");
        sharedFrame.add(loopButton);

        dislikeButton = new JButton("Dislike Song");
        sharedFrame.add(dislikeButton);

        nextButton = new JButton("Next Song");
        sharedFrame.add(nextButton);

        resetButton = new JButton("Reset Song");
        sharedFrame.add(resetButton);

        // Status display
        timerLabel = new JLabel("No Timer Set", SwingConstants.CENTER);
        sharedFrame.add(timerLabel);
    }

    // Getters for UI components
    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public JTextField getTimeInput() {
        return timeInput;
    }

    public JButton getPlayPauseButton() {
        return playPauseButton;
    }

    public JButton getTimerToggleButton() {
        return timerToggleButton;
    }

    public JButton getLoopButton() {
        return loopButton;
    }

    public JButton getDislikeButton() {
        return dislikeButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }
}
