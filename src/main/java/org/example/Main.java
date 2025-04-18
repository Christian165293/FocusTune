package org.example;

public class Main {
    public static void main(String[] args) {
        MusicQueue musicQueue = new MusicQueue();
        MusicRatings musicRatings = new MusicRatings();
        InitializePlaylist.fillMusicQueue(musicQueue, musicRatings);
        if (musicQueue.isEmpty()) {
            System.out.println("Input waves files folder is currently empty");
            System.out.println("please add wave files and reset program");
            return;
        }
        Application app = new Application(musicQueue, musicRatings);
        app.run();
    }
}