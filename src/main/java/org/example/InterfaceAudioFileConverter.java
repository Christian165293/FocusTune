package org.example;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public interface InterfaceAudioFileConverter {
    AudioInputStream getAudioInputStream(File file) throws UnsupportedAudioFileException, IOException;
}
