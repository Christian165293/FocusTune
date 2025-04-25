package org.example;


import java.util.HashMap;

//Maintains a HashMap mapping song paths to boolean values (liked/disliked)
public class MusicRatings implements InterfaceMusicRatings {
    private final HashMap<String, Boolean> ratings = new HashMap<>();    // Song path → liked (true/false)

    //Add new file to the rating system with the default value of true for "liked"
    @Override
    public void addFileToHashMap(String fullPathInput) {
        ratings.put(fullPathInput, true);
    }

    //Marks a song as disliked by setting a keys value to false
    @Override
    public void dislikeSong(String fullPathInput) {
        ratings.replace(fullPathInput, false);
    }

    //Returns true if all songs are disliked
    @Override
    public boolean checkForLikedRatings() {
        for (Boolean value : ratings.values()) {
            if (value) {
                return false;
            }
        }
        return true;
    }

    //get the boolean status of a sing
    @Override
    public boolean getSongStatus(String fullPathInput) {
        Boolean status = ratings.get(fullPathInput);
        return status != null ? status : true; // Default to "liked" if not found
    }

    // reset all map values back to the default of true for "liked"
    @Override
    public void resetAllRatings() {
        for (String key : ratings.keySet()) {
            ratings.replace(key, true);
        }
    }
}