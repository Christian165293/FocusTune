package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Populates the MusicQueue by reading MP3 files from a directory
public class InitializePlaylist {
    public static void fillMusicQueue(MusicQueue musicQueueInput, MusicRatings musicRatingsInput) {
        Path projectRoot = Paths.get(System.getProperty("user.dir"));
        Path folderPath = projectRoot.resolve("src/InputMP3FilesHere");

        try (Stream<Path> paths = Files.list(folderPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".mp3")) // Only accept .mp3 files
                    .forEach(path -> {
                        String fullPath = path.toString();
                        // Validate the MP3 file before adding it
                        if (isValidMP3File(fullPath)) {
                            musicQueueInput.add(fullPath);
                            musicRatingsInput.addFileToHashMap(fullPath);
                            System.out.println("Added to queue: " + fullPath);
                        } else {
                            System.out.println("Skipped invalid MP3 file: " + fullPath);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Error reading audio files: " + e.getMessage());
        }
    }

    // Simple validation method for MP3 files
    private static boolean isValidMP3File(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Try to read the first few bytes of the file
            byte[] header = new byte[4];
            if (fis.read(header) != 4) {
                return false;
            }

            // Check for MP3 file signature - simple check for ID3v2 tag or MP3 sync word
            return (header[0] == 'I' && header[1] == 'D' && header[2] == '3') ||
                    ((header[0] & 0xFF) == 0xFF && (header[1] & 0xE0) == 0xE0);
        } catch (Exception e) {
            System.out.println("Error validating MP3 file " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
