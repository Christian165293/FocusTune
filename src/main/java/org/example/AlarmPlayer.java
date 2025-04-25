package org.example;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

//Class to play an alarm sound when the timer completes
//uses the JLayer Library javazoom to play the mp3 files
public class AlarmPlayer {
    public void playAlarm() {
        System.out.println("Attempting to play alarm...");
        //try's to load and play the file AlarmSound.mp3 and catches
        // if it cannot be found or played
        try {
            //opens alarm file
            FileInputStream fileInputStream = new FileInputStream("AlarmSound.mp3");
            //Creates the JLayer Player to play the alarm
            Player player = new Player(fileInputStream);
            player.play();
            System.out.println("Alarm played successfully");
        } catch (Exception e) {
            System.out.println("Problem playing alarm file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
