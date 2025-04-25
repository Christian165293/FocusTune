package org.example;

//Simple controller for the loop functionality (on/off)
public class LoopManager {
    private boolean loopEnabled = false;

    public void enableLoop() {
        loopEnabled = true;
    }

    public void disableLoop() {
        loopEnabled = false;
    }

    //Checks if the loop feature is currently enabled
    public boolean isLoopEnabled() {
        return loopEnabled;
    }
}
