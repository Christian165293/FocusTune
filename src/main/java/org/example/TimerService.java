package org.example;

import javax.swing.*;

//Implements a countdown timer with callbacks
public class TimerService {
    private final javax.swing.Timer timer;
    private int remainingSeconds;
    private boolean isRunning = false;
    private TimerListener listener; // Default listener

    public interface TimerListener {
        void onTimerTick(int remainingSeconds);
        void onTimerComplete();
    }

    // Default listener implementation that does nothing
    private static class DefaultTimerListener implements TimerListener {
        @Override
        public void onTimerTick(int remainingSeconds) {
            System.out.println("Default listener: Timer tick - " + remainingSeconds);
        }

        @Override
        public void onTimerComplete() {
            System.out.println("Default listener: Timer completed");
        }
    }

    public TimerService(TimerListener initialListener) {
        System.out.println("TimerService created with listener: " + (initialListener != null));

        // Use provided listener or default listener
        this.listener = initialListener != null ? initialListener : new DefaultTimerListener();

        this.timer = new javax.swing.Timer(1000, _ -> {
            remainingSeconds--;
            System.out.println("Timer ticking: " + remainingSeconds + " seconds remaining");

            // Always notify listener (won't be null because of default)
            notifyListener();

            if (remainingSeconds <= 0) {
                stopTimer();
                // No null check needed - we have default listener
                SwingUtilities.invokeLater(() -> listener.onTimerComplete());
            }
        });
        this.timer.setRepeats(true);
        this.timer.setCoalesce(true);
    }

    private void notifyListener() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Notifying listener with: " + remainingSeconds);
            listener.onTimerTick(remainingSeconds);
        });
    }

    public void startTimer(int minutes) {
        System.out.println("Starting timer for " + minutes + " minutes");
        this.remainingSeconds = minutes * 60;
        this.isRunning = true;

        // Immediately notify listener of initial time
        notifyListener();

        timer.start();
    }

    public void stopTimer() {
        System.out.println("Stopping timer");
        timer.stop();
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setListener(TimerListener newListener) {
        System.out.println("Setting new listener: " + (newListener != null));
        this.listener = newListener != null ? newListener : new DefaultTimerListener();
    }
}
