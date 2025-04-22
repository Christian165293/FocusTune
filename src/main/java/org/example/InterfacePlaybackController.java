package org.example;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

// New interface for the controller
public interface InterfacePlaybackController {
    void loadAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    void startPlayback();

    void stopPlayback();

    void reset();

    void close();

    void setOnPlaybackComplete(Runnable callback);
}
