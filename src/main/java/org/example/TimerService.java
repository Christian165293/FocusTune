package org.example;

import javax.swing.*;

//Implements a countdown timer with callbacks
public class TimerService {
    private final javax.swing.Timer timer;//The timer component
    private int remainingSeconds;//Seconds left on the timer
    private boolean isRunning = false;//The current timer state
    private TimerListener listener; // Default listener

    //Interface for defining the callback methods for timer events
    public interface TimerListener {
        void onTimerTick(int remainingSeconds);//Called every second with remaining time

        void onTimerComplete();//Called when timer reaches zero
    }

    //Default listener for when no listener is provided(mainly used for error tracking)
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
    //Constructor that initializer the timer with a listener for callbacks
    public TimerService(TimerListener initialListener) {
        System.out.println("TimerService created with listener: " + (initialListener != null));

        // Use provided listener or default listener if null
        this.listener = initialListener != null ? initialListener : new DefaultTimerListener();

        //create Swing timer that executes ever 1 second or 1000 milliseconds
        this.timer = new javax.swing.Timer(1000, _ -> {
            remainingSeconds--;
            System.out.println("Timer ticking: " + remainingSeconds + " seconds remaining");

            // Always notify listener (won't be null because of default)
            notifyListener();

            if (remainingSeconds <= 0) {
                stopTimer();
                // Schedule completion notification on Swing thread for UI safety
                SwingUtilities.invokeLater(() -> listener.onTimerComplete());
            }
        });
        this.timer.setRepeats(true);//timer should repeat until stopped
        this.timer.setCoalesce(true);//merge missed ticks if the system was busy
    }

    //method to safely notify listener on timer ticks
    private void notifyListener() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Notifying listener with: " + remainingSeconds);
            listener.onTimerTick(remainingSeconds);
        });
    }

    //starts the timer for the specified duration
    public void startTimer(int minutes) {
        System.out.println("Starting timer for " + minutes + " minutes");
        this.remainingSeconds = minutes * 60;
        this.isRunning = true;

        // Immediately notify listener of initial time
        notifyListener();

        timer.start();
    }

    //stop timer before completion
    public void stopTimer() {
        System.out.println("Stopping timer");
        timer.stop();
        this.isRunning = false;
    }
    //return true if timer is currently running
    public boolean isRunning() {
        return isRunning;
    }
    //updates the timer listener
    public void setListener(TimerListener newListener) {
        System.out.println("Setting new listener: " + (newListener != null));
        this.listener = newListener != null ? newListener : new DefaultTimerListener();
    }
}
