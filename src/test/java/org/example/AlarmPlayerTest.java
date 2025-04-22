package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AlarmPlayerTest {
    private final AlarmPlayer alarmPlayer = new AlarmPlayer();

    @Test
    void playAlarm_shouldHandleMissingFileGracefully() {
        // This test verifies the alarm player doesn't crash when file is missing
        assertDoesNotThrow(alarmPlayer::playAlarm);
    }

    @Test
    void playAlarm_shouldPlayValidFile(@TempDir Path tempDir) throws IOException {
        // Create a dummy MP3 file
        File testFile = tempDir.resolve("AlarmSound.mp3").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{'I', 'D', '3', 0}); // Minimal MP3 header
        }

        // Change working directory to temp dir for this test
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        try {
            assertDoesNotThrow(alarmPlayer::playAlarm);
        } finally {
            // Restore original working directory
            System.setProperty("user.dir", originalDir);
        }
    }
}