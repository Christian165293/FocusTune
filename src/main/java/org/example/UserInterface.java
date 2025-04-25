package org.example;

import javax.swing.*;
import java.awt.*;

//UserInterface handles the visual components and styling
public class UserInterface {
    // UI component references
    private final JLabel timerLabel; // Displays timer status
    private final JTextField timeInput; // Input field for timer duration
    private final JButton playPauseButton; // Controls music playback
    private final JButton timerToggleButton; // Starts/stops the timer
    private final JButton loopButton; // Toggles song looping
    private final JButton dislikeButton; // Marks current song as disliked
    private final JButton nextButton; // Skips to next song
    private final JButton resetButton; // Restarts current song
    private final JButton quitButton; // Exits the application

    // Color scheme constants for consistent UI styling
    private static final Color BACKGROUND_DARK = new Color(0x2D2D2D); // Dark background
    private static final Color ACCENT_GREEN = new Color(0x4CAF50); // Green accent for buttons
    private static final Color TEXT_WHITE = Color.WHITE; // White text
    private static final Color SECONDARY_GRAY = new Color(0x3E3E3E); // Gray for input fields

    //Constructor that initializes and configures all UI components
    public UserInterface(JFrame sharedFrame) {
        sharedFrame.getContentPane().setBackground(BACKGROUND_DARK);
        sharedFrame.setLayout(new GridLayout(6, 2, 5, 5));
        sharedFrame.setSize(400, 300);
        sharedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Timer input label
        JLabel timerPrompt = new JLabel("Timer (minutes):", SwingConstants.CENTER);
        timerPrompt.setForeground(TEXT_WHITE);
        timerPrompt.setBackground(BACKGROUND_DARK);
        sharedFrame.add(timerPrompt);

        // Timer input field
        timeInput = new JTextField(5);
        timeInput.setHorizontalAlignment(JTextField.CENTER);
        timeInput.setBackground(SECONDARY_GRAY);
        timeInput.setForeground(TEXT_WHITE);
        timeInput.setCaretColor(TEXT_WHITE);
        timeInput.setBorder(BorderFactory.createLineBorder(ACCENT_GREEN, 1));
        sharedFrame.add(timeInput);

        // Create all buttons with consistent styling
        playPauseButton = createStyledButton("Play Music");
        sharedFrame.add(playPauseButton);

        timerToggleButton = createStyledButton("Start Timer");
        sharedFrame.add(timerToggleButton);

        loopButton = createStyledButton("Loop Music");
        sharedFrame.add(loopButton);

        dislikeButton = createStyledButton("Dislike Song");
        sharedFrame.add(dislikeButton);

        nextButton = createStyledButton("Next Song");
        sharedFrame.add(nextButton);

        resetButton = createStyledButton("Reset Song");
        sharedFrame.add(resetButton);

        quitButton = createStyledButton("Quit");
        sharedFrame.add(quitButton);

        // Status display label
        timerLabel = new JLabel("No Timer Set", SwingConstants.CENTER);
        timerLabel.setForeground(TEXT_WHITE);
        timerLabel.setBackground(BACKGROUND_DARK);
        sharedFrame.add(timerLabel);
    }

    // Helper method to create consistently styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT_GREEN);
        button.setForeground(TEXT_WHITE);
        button.setFocusPainted(false); // Remove focus outline
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x1E1E1E), 2), // Outer dark border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Inner padding
        ));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setOpaque(true); // Ensure background color is visible
        return button;
    }

    // Getters for UI components
    // These allow the UserControls class to access and manipulate UI elements
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

    public JButton getQuitButton() {
        return quitButton;
    }
}
