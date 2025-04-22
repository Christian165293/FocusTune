package org.example;

public class LoopManager {
    private boolean loopEnabled = false;

    public void enableLoop() {
        loopEnabled = true;
    }

    public void disableLoop() {
        loopEnabled = false;
    }

    public boolean isLoopEnabled() {
        return loopEnabled;
    }
}
