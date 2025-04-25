package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//Handles MP3 audio playback using JavaSound API
//Manages loading, playing, stopping and monitoring audio files
public class AudioPlaybackController implements InterfacePlaybackController {
    private Clip clip;// the audio clip being played
    private boolean manualStop;//Flag which indicated if the audio was manually stopped
    private Runnable playbackCompleteCallback;//Callback to run when playback is completed
    private final LineListener endListener;//Listener to listen for the start and stop of an audio event
    private final InterfaceAudioFileConverter audioFileConverter;//the audio file conversion

    // Constructor for converting the audio files
    public AudioPlaybackController(InterfaceAudioFileConverter audioFileConverter) {
        this.audioFileConverter = audioFileConverter;
        //creation of the listener
        this.endListener = event -> {
            System.out.println("Line event: " + event.getType());

            if (event.getType() == LineEvent.Type.STOP) {
                System.out.println("Stop event detected. manualStop=" + manualStop);
                if (clip != null) {
                    System.out.println("Position: " + clip.getMicrosecondPosition() +
                            ", Length: " + clip.getMicrosecondLength());
                }
//determining if playback stopped naturally or by user, allows 1 second of tolerance
                if (!manualStop && clip != null &&
                        clip.getMicrosecondPosition() >= clip.getMicrosecondLength() - 1000000) {
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

    //sets the callback function to be called when the playback is completed naturally
    @Override
    public void setOnPlaybackComplete(Runnable callback) {
        this.playbackCompleteCallback = callback;
        System.out.println("Playback complete callback set");
    }

    //load next audio file for playback and coverts the file to correct format
    @Override
    public void loadAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("Loading audio: " + filePath);
//clean up existing audio clip
        if (clip != null) {
            clip.removeLineListener(endListener);
            clip.close();
        }

        // convert the audio to playable stream using converter
        try (AudioInputStream audioStream = audioFileConverter.getAudioInputStream(new File(filePath))) {
            AudioFormat baseFormat = audioStream.getFormat();

            //format for PCM conversion which is used by Java Sound API
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

            //create and prepare the actual audio clip
            clip = AudioSystem.getClip();
            clip.open(decodedStream);
            clip.addLineListener(endListener);

            System.out.println("Audio loaded successfully, duration: " + clip.getMicrosecondLength() / 1000000.0 + " seconds");
        }
    }

    //start playing next loaded audio
    @Override
    public void startPlayback() {
        if (clip != null) {
            manualStop = false;
            System.out.println("Starting playback");
            clip.start();
        }
    }

    //stop the audio playback
    @Override
    public void stopPlayback() {
        if (clip != null) {
            manualStop = true;
            System.out.println("Stopping playback (manual)");
            clip.stop();
        }
    }

    //reset the current clips audio position to beginning which resets song
    @Override
    public void reset() {
        if (clip != null) {
            System.out.println("Resetting playback position to beginning");
            clip.setMicrosecondPosition(0);
        }
    }

    //stops all the current resources which are associated with the audio playback
    @Override
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
