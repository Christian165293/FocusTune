package org.example;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

//classes for converting different audio formats to AudioInputStream
//objects wich are then played by the Java Sound API
public class AudioFileConverter {
    //MP3 files conversion wich uses MpegAudioFileReader to read MP3 files
    public static class MP3FileReader implements InterfaceAudioFileConverter {
        @Override
        public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
            return new MpegAudioFileReader().getAudioInputStream(file);
        }
    }

    //WAV files conversion which using Java's built in audio system to read WAV files
    public static class WAVFileReader implements InterfaceAudioFileConverter {
        @Override
        public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
            return javax.sound.sampled.AudioSystem.getAudioInputStream(file);
        }
    }
}
