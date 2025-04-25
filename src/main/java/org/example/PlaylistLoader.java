package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
//Loads music files into the queue by scanning the directory for valid MP3 files
//which will be added while the invalid are discarded
public class PlaylistLoader implements InterfacePlaylistLoader{
    private final Path directoryPath;//Directory to scan files from
    private final FileValidator fileValidator;//Validating the files for validity

    //Constructor which initializes the loader with a directory path and a file validator
    public PlaylistLoader(Path directoryPath, FileValidator fileValidator) {
        this.directoryPath = directoryPath;
        this.fileValidator = fileValidator;
    }
    //Load the files into music queue and initialize rating
    @Override
    public void loadPlaylist(InterfaceMusicQueue musicQueue, InterfaceMusicRatings musicRatings) {
        try (Stream<Path> paths = Files.list(directoryPath)) {//List all files in directory
            paths.filter(Files::isRegularFile)//Filtering for only regular files and not other directories
                    .filter(path -> path.toString().toLowerCase().endsWith(".mp3")) // Only accept .mp3 files
                    .forEach(path -> {
                        String fullPath = path.toString();
                        // Validate the MP3 file before adding it
                        if (fileValidator.isValid(fullPath)) {
                            musicQueue.add(fullPath);//Add the valid file to the queue
                            musicRatings.addFileToHashMap(fullPath);//initialize rating for the added file
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
