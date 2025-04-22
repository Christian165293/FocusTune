package org.example;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class AlarmPlayer {
    public void playAlarm() {
        try {
            FileInputStream fileInputStream = new FileInputStream("AlarmSound.mp3");
            Player player = new Player(fileInputStream);
            player.play();
        } catch (Exception e) {
            System.out.println("Problem playing file: " + e.getMessage());
        }
    }
}
