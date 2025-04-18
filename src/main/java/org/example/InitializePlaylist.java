package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InitializePlaylist {
    public static void fillMusicQueue(MusicQueue musicQueueInput, MusicRatings musicRatingsInput) {
        Path projectRoot = Paths.get(System.getProperty("user.dir"));
        Path folderPath = projectRoot.resolve("src/InputMP3FilesHere");

        try (Stream<Path> paths = Files.list(folderPath)) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        String fullPath = path.toString(); // Store full path
                        musicQueueInput.add(fullPath);
                        musicRatingsInput.addFileToHashMap(fullPath);
                    });
        } catch (IOException e) {
            System.out.println("Error reading audio files: " + e.getMessage());
        }
    }

}
