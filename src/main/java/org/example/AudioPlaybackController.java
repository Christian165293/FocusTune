package org.example;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlaybackController {
    private Clip clip;
    private boolean manualStop;
    private Runnable playbackCompleteCallback;
    private final LineListener endListener;

    public AudioPlaybackController() {
        this.endListener = event -> {
            if (event.getType() == LineEvent.Type.STOP &&
                    !manualStop && clip != null &&
                    clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                if (playbackCompleteCallback != null) {
                    playbackCompleteCallback.run();
                }
            }
            manualStop = false;
        };
    }

    public void setOnPlaybackComplete(Runnable callback) {
        this.playbackCompleteCallback = callback;
    }

    public void loadAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (clip != null) {
            clip.removeLineListener(endListener);
            clip.close();
        }

        // MP3-specific audio loading
        try (AudioInputStream audioStream = new MpegAudioFileReader().getAudioInputStream(new File(filePath))) {
            AudioFormat baseFormat = audioStream.getFormat();

            //format for PCM conversion
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);

            // Convert MP3 to PCM format
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            clip = AudioSystem.getClip();
            clip.open(decodedStream);
            clip.addLineListener(endListener);
        }
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
}
