package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//Handles MP3 audio playback using JavaSound API
public class AudioPlaybackController implements InterfacePlaybackController {
    private Clip clip;
    private boolean manualStop;
    private Runnable playbackCompleteCallback;
    private final LineListener endListener;
    private final InterfaceAudioFileConverter audioFileConverter;

    // Dependency injection for the audio file reader factory
    public AudioPlaybackController(InterfaceAudioFileConverter audioFileConverter) {
        this.audioFileConverter = audioFileConverter;
        this.endListener = event -> {
            System.out.println("Line event: " + event.getType());

            if (event.getType() == LineEvent.Type.STOP) {
                System.out.println("Stop event detected. manualStop=" + manualStop);
                if (clip != null) {
                    System.out.println("Position: " + clip.getMicrosecondPosition() +
                            ", Length: " + clip.getMicrosecondLength());
                }

                if (!manualStop && clip != null &&
                        clip.getMicrosecondPosition() >= clip.getMicrosecondLength() - 1000000) { // Allow 1 second tolerance
                    System.out.println("End of track detected, calling completion callback");
                    if (playbackCompleteCallback != null) {
                        playbackCompleteCallback.run();
                    }
                }
            }

            // Only reset manualStop after processing stop events
            if (event.getType() == LineEvent.Type.STOP) {
                manualStop = false;
            }
        };
    }

    @Override
    public void setOnPlaybackComplete(Runnable callback) {
        this.playbackCompleteCallback = callback;
        System.out.println("Playback complete callback set");
    }

    @Override
    public void loadAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("Loading audio: " + filePath);

        if (clip != null) {
            clip.removeLineListener(endListener);
            clip.close();
        }

        // Using the injected converter to get the appropriate audio stream
        try (AudioInputStream audioStream = audioFileConverter.getAudioInputStream(new File(filePath))) {
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

            // Convert to PCM format
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            clip = AudioSystem.getClip();
            clip.open(decodedStream);
            clip.addLineListener(endListener);

            System.out.println("Audio loaded successfully, duration: " + clip.getMicrosecondLength() / 1000000.0 + " seconds");
        }
    }

    @Override
    public void startPlayback() {
        if (clip != null) {
            manualStop = false;
            System.out.println("Starting playback");
            clip.start();
        }
    }

    @Override
    public void stopPlayback() {
        if (clip != null) {
            manualStop = true;
            System.out.println("Stopping playback (manual)");
            clip.stop();
        }
    }

    @Override
    public void reset() {
        if (clip != null) {
            System.out.println("Resetting playback position to beginning");
            clip.setMicrosecondPosition(0);
        }
    }

    @Override
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
