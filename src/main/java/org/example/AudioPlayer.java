package org.example;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private Clip clip;
    private boolean loopEnabled = false;
    boolean manualStop;
    LineListener endListener = event -> {
        if (event.getType() == LineEvent.Type.STOP) {
            if (!manualStop && clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                // Only trigger next song if playback reached the end (not paused)
                if (loopEnabled) {
                    reset();
                    play();
                } else {
                    try {
                        nextSong();
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            manualStop = false; // Reset for next playback
        }
    };
    MusicQueue musicQueue;
    MusicRatings musicRatings;

    public AudioPlayer(MusicQueue musicQueueInput, MusicRatings musicRatingsInput) {
        musicQueue = musicQueueInput;
        musicRatings = musicRatingsInput;
    }

    public void loadAudio() throws UnsupportedAudioFileException,
            IOException,
            LineUnavailableException {
        File file = new File(musicQueue.peek());
        AudioInputStream audioStream = new MpegAudioFileReader().getAudioInputStream(file);
        AudioFormat baseFormat = audioStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,//PCM_SIGNED encoding (standard for WAVs and Java Sound).
                baseFormat.getSampleRate(),
                16,//16-bit depth (CD-quality resolution).
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,//Correct frame size (2 bytes per sample for 16-bit audio).
                baseFormat.getSampleRate(),
                false);//prioritize low value byes first
        AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
        clip = AudioSystem.getClip();
        clip.open(decodedStream);
        endSongListener();
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            manualStop = true; // Mark that STOP was manual
            clip.stop();
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

    public void nextSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        do {
            stop();
            clip.removeLineListener(endListener);
            close();
            if (!loopEnabled) {
                musicQueue.add(musicQueue.peek()); // Re-add current song to queue
                musicQueue.remove(); // Move to next song
            }
            loadAudio();
            play();
        } while (!musicRatings.getSongStatus(musicQueue.peek()));
    }


    public void endSongListener() {
        clip.addLineListener(endListener);
    }

    public void enableLoop() {
        loopEnabled = true;
    }

    public void disableLoop() {
        loopEnabled = false;
    }
}
