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

    public boolean checkForLikedRatings(){
        for (Boolean value : ratings.values()) {
            if (value) {
                return true;
            }
        }
        return false;
    }

    public boolean getSongStatus(String fullPathInput) {
        Boolean status = ratings.get(fullPathInput);
        return status != null ? status : true; // Default to "liked" if not found
    }

    public void resetAllRatings() {
        for (String key : ratings.keySet()) {
            ratings.replace(key, true);
        }
    }
}