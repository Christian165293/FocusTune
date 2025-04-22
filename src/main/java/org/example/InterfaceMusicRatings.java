package org.example;

// Interface for ratings operations
public interface InterfaceMusicRatings {
    void addFileToHashMap(String fullPath);

    void dislikeSong(String fullPath);

    boolean checkForLikedRatings();

    boolean getSongStatus(String fullPath);

    void resetAllRatings();
}
