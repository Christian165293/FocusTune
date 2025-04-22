package org.example;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

//Class to play an alarm sound when the timer completes
public class AlarmPlayer {
    public void playAlarm() {
        System.out.println("Attempting to play alarm...");
        try {
            FileInputStream fileInputStream = new FileInputStream("AlarmSound.mp3");
            System.out.println("Alarm file found, playing...");
            Player player = new Player(fileInputStream);
            player.play();
            System.out.println("Alarm played successfully");
        } catch (Exception e) {
            System.out.println("Problem playing alarm file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
