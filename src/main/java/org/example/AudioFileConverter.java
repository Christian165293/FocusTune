package org.example;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioFileConverter{
    //MP3 files conversion
    public static class MP3FileReader implements InterfaceAudioFileConverter {
        @Override
        public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
            return new MpegAudioFileReader().getAudioInputStream(file);
        }
    }

    //WAV files conversion
    public static class WAVFileReader implements InterfaceAudioFileConverter {
        @Override
        public AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
            return javax.sound.sampled.AudioSystem.getAudioInputStream(file);
        }
    }
}
