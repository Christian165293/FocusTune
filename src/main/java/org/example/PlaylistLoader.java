package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PlaylistLoader implements InterfacePlaylistLoader{
    private final Path directoryPath;
    private final FileValidator fileValidator;

    public PlaylistLoader(Path directoryPath, FileValidator fileValidator) {
        this.directoryPath = directoryPath;
        this.fileValidator = fileValidator;
    }

    @Override
    public void loadPlaylist(InterfaceMusicQueue musicQueue, InterfaceMusicRatings musicRatings) {
        try (Stream<Path> paths = Files.list(directoryPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".mp3")) // Only accept .mp3 files
                    .forEach(path -> {
                        String fullPath = path.toString();
                        // Validate the MP3 file before adding it
                        if (fileValidator.isValid(fullPath)) {
                            musicQueue.add(fullPath);
                            musicRatings.addFileToHashMap(fullPath);
                            System.out.println("Added to queue: " + fullPath);
                        } else {
                            System.out.println("Skipped invalid MP3 file: " + fullPath);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Error reading audio files: " + e.getMessage());
        }
    }
}
