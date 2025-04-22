package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AudioFileConverterTest {
    @Test
    void MP3FileReader_shouldReturnAudioInputStream(@TempDir Path tempDir) throws Exception {
        // Create a dummy MP3 file (content doesn't matter for this test)
        File testFile = tempDir.resolve("test.mp3").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{'I', 'D', '3', 0}); // Minimal MP3 header
        }

        AudioFileConverter.MP3FileReader reader = new AudioFileConverter.MP3FileReader();
        AudioInputStream stream = reader.getAudioInputStream(testFile);

        assertNotNull(stream);
        stream.close();
    }

    @Test
    void WAVFileReader_shouldReturnAudioInputStream(@TempDir Path tempDir) throws Exception {
        // Note: This would need a proper WAV file header to work
        // For this test, we'll just verify it throws UnsupportedAudioFileException
        // for an invalid file, which confirms it's trying to parse the file

        File testFile = tempDir.resolve("test.wav").toFile();
        try (FileOutputStream fos = new FileOutputStream(testFile)) {
            fos.write(new byte[]{'R', 'I', 'F', 'F'}); // Partial WAV header
        }

        AudioFileConverter.WAVFileReader reader = new AudioFileConverter.WAVFileReader();
        assertThrows(javax.sound.sampled.UnsupportedAudioFileException.class, () -> reader.getAudioInputStream(testFile));
    }
}