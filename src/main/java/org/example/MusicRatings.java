package org.example;


import java.util.HashMap;

//Maintains a HashMap mapping song paths to boolean values (liked/disliked)
public class MusicRatings implements InterfaceMusicRatings{
    private final HashMap<String, Boolean> ratings = new HashMap<>();    // Song path → liked (true/false)

    @Override
    public void addFileToHashMap(String fullPathInput) {
        ratings.put(fullPathInput, true);
    }

    @Override
    public void dislikeSong(String fullPathInput) {
        ratings.replace(fullPathInput, false);
    }

    @Override
    public boolean checkForLikedRatings() {
        for (Boolean value : ratings.values()) {
            if (value) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean getSongStatus(String fullPathInput) {
        Boolean status = ratings.get(fullPathInput);
        return status != null ? status : true; // Default to "liked" if not found
    }

    @Override
    public void resetAllRatings() {
        for (String key : ratings.keySet()) {
            ratings.replace(key, true);
        }
    }
}