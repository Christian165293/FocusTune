package org.example;

import javax.swing.*;
import java.awt.*;

//UserInterface handles the visual components and styling
public class UserInterface {
    private final JLabel timerLabel;
    private final JTextField timeInput;
    private final JButton playPauseButton;
    private final JButton timerToggleButton;
    private final JButton loopButton;
    private final JButton dislikeButton;
    private final JButton nextButton;
    private final JButton resetButton;
    private final JButton quitButton;

    // Color constants
    private static final Color BACKGROUND_DARK = new Color(0x2D2D2D);
    private static final Color ACCENT_GREEN = new Color(0x4CAF50);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color SECONDARY_GRAY = new Color(0x3E3E3E);

    public UserInterface(JFrame sharedFrame) {
        // Configure the shared frame
        sharedFrame.getContentPane().setBackground(BACKGROUND_DARK);
        sharedFrame.setLayout(new GridLayout(6, 2, 5, 5));
        sharedFrame.setSize(400, 300);
        sharedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Timer input components
        JLabel timerPrompt = new JLabel("Timer (minutes):", SwingConstants.CENTER);
        timerPrompt.setForeground(TEXT_WHITE);
        timerPrompt.setBackground(BACKGROUND_DARK);
        sharedFrame.add(timerPrompt);

        timeInput = new JTextField(5);
        timeInput.setHorizontalAlignment(JTextField.CENTER);
        timeInput.setBackground(SECONDARY_GRAY);
        timeInput.setForeground(TEXT_WHITE);
        timeInput.setCaretColor(TEXT_WHITE);
        timeInput.setBorder(BorderFactory.createLineBorder(ACCENT_GREEN, 1));
        sharedFrame.add(timeInput);

        // Initialize buttons with consistent styling
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

        // Status display
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
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x1E1E1E), 2), // Outer border (dark)
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Inner padding
        ));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setOpaque(true);
        return button;
    }

    // Getters for UI components (unchanged)
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
