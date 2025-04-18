package org.example;

import java.util.HashMap;

public class MusicRatings {
    private final HashMap<String, Boolean> ratings = new HashMap<>();    // Song path → liked (true/false)

    public void addFileToHashMap(String fullPathInput) {
        ratings.put(fullPathInput, true);
    }

    public void dislikeSong(String fullPathInput) {
        ratings.replace(fullPathInput, false);
    }

    public boolean getSongStatus(String fullPathInput) {
        Boolean status = ratings.get(fullPathInput);
        return status != null ? status : true; // Default to "liked" if not found
    }
}