package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileValidatorTest {
    private final FileValidator validator = new FileValidator();

    @Test
    void isValid_shouldReturnTrueForValidMP3WithID3Tag(@TempDir Path tempDir) throws IOException {
        // Create a test file with ID3 header
        File testFile = tempDir.resolve("test.mp3").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{'I', 'D', '3', 0}); // ID3v2 header
        }

        assertTrue(validator.isValid(testFile.getAbsolutePath()));
    }

    @Test
    void isValid_shouldReturnTrueForValidMP3WithSyncWord(@TempDir Path tempDir) throws IOException {
        // Create a test file with MP3 sync word
        File testFile = tempDir.resolve("test.mp3").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{(byte) 0xFF, (byte) 0xFB, 0, 0}); // MP3 sync word
        }

        assertTrue(validator.isValid(testFile.getAbsolutePath()));
    }

    @Test
    void isValid_shouldReturnFalseForInvalidFile(@TempDir Path tempDir) throws IOException {
        // Create a test file with invalid header
        File testFile = tempDir.resolve("test.mp3").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{0, 0, 0, 0}); // Invalid header
        }

        assertFalse(validator.isValid(testFile.getAbsolutePath()));
    }

    @Test
    void isValid_shouldReturnFalseForNonExistentFile() {
        assertFalse(validator.isValid("nonexistent.mp3"));
    }

    @Test
    void isValid_shouldReturnFalseForEmptyFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("empty.mp3").toFile();
        testFile.createNewFile(); // Create empty file

        assertFalse(validator.isValid(testFile.getAbsolutePath()));
    }
}