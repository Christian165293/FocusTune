package org.example;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Application {
    private final MusicQueue musicQueue;
    private final MusicRatings musicRatings;
    private final AudioPlayer audioPlayer;
    private final UserInterface userInterface = new UserInterface();

    public Application(MusicQueue musicQueueInput, MusicRatings musicRatingsInput) {
        musicQueue = musicQueueInput;
        musicRatings = musicRatingsInput;
        audioPlayer = new AudioPlayer(musicQueue, musicRatings); // Initialize after fields are set
    }
    public void run() {
        try {
            audioPlayer.loadAudio();
            userInterface.displayMessage("Audio player ready");

            String response = "";
            while (!response.equals("Q")) {
                userInterface.displayMenu();
                response = userInterface.getInput();

                switch (response) {
                    case "P" -> audioPlayer.play();
                    case "S" -> audioPlayer.stop();
                    case "N" -> audioPlayer.nextSong();
                    case "D" -> {
                        musicRatings.dislikeSong(musicQueue.peek());
                        audioPlayer.nextSong();
                    }
                    case "R" -> audioPlayer.reset();
                    case "EL" -> {
                        audioPlayer.enableLoop();
                        System.out.println("loop enabled");
                    }
                    case "DL" ->{
                        audioPlayer.disableLoop();
                        System.out.println("loop enabled");
                    }
                    case "Q" -> {
                        audioPlayer.close();
                        userInterface.displayMessage("Goodbye!");
                    }
                    default -> userInterface.displayMessage("Invalid option");
                }
            }
        } catch (UnsupportedAudioFileException e) {
            userInterface.displayMessage("Audio file is not supported");
            userInterface.displayMessage("Please input a .wav audio file");
        } catch (LineUnavailableException e) {
            userInterface.displayMessage("Unable to access audio resource");
        } catch (IOException e) {
            userInterface.displayMessage("Error: " + e.getMessage());
        } finally {
            userInterface.close();
        }
    }
}
