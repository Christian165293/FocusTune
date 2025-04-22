package org.example;

import javazoom.jl.player.Player;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {
    private Clip clip;
    private boolean loopEnabled = false;
    private boolean manualStop;
    private final MusicQueue musicQueue;
    private final MusicRatings musicRatings;
    private String currentSong;

    private final LineListener endListener = event -> {
        if (event.getType() == LineEvent.Type.STOP) {
            if (!manualStop && clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                handlePlaybackComplete();
            }
            manualStop = false;
        }
    };

    public String getCurrentSong() {
        return currentSong;
    }

    public boolean isLoopEnabled() {
        return loopEnabled;
    }

    public MusicRatings getMusicRatings() {
        return musicRatings;
    }

    public void dislikeCurrentSong() {
        if (currentSong != null) {
            musicRatings.dislikeSong(currentSong);
        }
    }

    public boolean checkIfRatingsFalse(){
        return musicRatings.checkForLikedRatings();
    }

    public AudioPlayer(MusicQueue musicQueue, MusicRatings musicRatings) {
        this.musicQueue = musicQueue;
        this.musicRatings = musicRatings;
    }

    public void loadAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        currentSong = musicQueue.peek();
        File file = new File(currentSong);
        AudioInputStream audioStream = new MpegAudioFileReader().getAudioInputStream(file);
        AudioFormat baseFormat = audioStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
        clip = AudioSystem.getClip();
        clip.open(decodedStream);
        clip.addLineListener(endListener);
    }

    public void startPlayback() {
        if (clip != null) {
            manualStop = false;
            clip.start();
        }
    }

    public void stopPlayback() {
        if (clip != null) {
            manualStop = true;
            clip.stop();
        }
    }

    public void playAlarm(){
        try {
            FileInputStream fileInputStream = new FileInputStream("AlarmSound.mp3");
            Player player = new Player(fileInputStream);
            player.play();
        } catch (Exception e) {
            System.out.println("Problem playing file: " + e.getMessage());
        }
    }

    public void reset() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }

    private void handlePlaybackComplete() {
        if (loopEnabled) {
            reset();
            startPlayback();
        } else {
            try {
                nextSong();
            } catch (Exception e) {
                System.err.println("Error moving to next song: " + e.getMessage());
            }
        }
    }

    public void nextSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // First check if we have any liked songs left
        if (!musicRatings.checkForLikedRatings()) {
            musicRatings.resetAllRatings();
            if (musicQueue.isEmpty()) {
                return; // No songs in queue
            }
        }

        // Find the next liked song without playing disliked ones
        String nextSong = findNextLikedSong();
        if (nextSong == null) {
            throw new IllegalStateException("No playable song found");
        }

        stopPlayback();
        clip.removeLineListener(endListener);
        close();

        currentSong = nextSong;
        loadAudio();
        startPlayback();
    }

    private String findNextLikedSong() {
        int attempts = 0;
        int maxAttempts = musicQueue.isEmpty() ? 0 : musicQueue.getSize();

        while (attempts < maxAttempts) {
            String candidate = musicQueue.peek();
            if (musicRatings.getSongStatus(candidate)) {
                return candidate;
            }

            // Move disliked song to end of queue
            musicQueue.add(musicQueue.peek());
            musicQueue.remove();
            attempts++;
        }

        return null;
    }

    public void enableLoop() {
        loopEnabled = true;
    }

    public void disableLoop() {
        loopEnabled = false;
    }
}
