package org.example;


import org.junit.jupiter.api.Test;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {
    @Test
    void createStyledButton_shouldReturnStyledButton() {
        JFrame dummyFrame = new JFrame();
        UserInterface ui = new UserInterface(dummyFrame);

        JButton button = ui.getPlayPauseButton();
        assertNotNull(button);
        assertEquals("Play Music", button.getText());
        assertTrue(button.isOpaque());
    }

    @Test
    void uiComponents_shouldBeAccessible() {
        JFrame dummyFrame = new JFrame();
        UserInterface ui = new UserInterface(dummyFrame);

        assertNotNull(ui.getTimerLabel());
        assertNotNull(ui.getTimeInput());
        assertNotNull(ui.getPlayPauseButton());
        assertNotNull(ui.getTimerToggleButton());
        assertNotNull(ui.getLoopButton());
        assertNotNull(ui.getDislikeButton());
        assertNotNull(ui.getNextButton());
        assertNotNull(ui.getResetButton());
        assertNotNull(ui.getQuitButton());
    }
}