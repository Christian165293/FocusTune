package org.example;

public class TimerService {
    private final javax.swing.Timer timer;
    private int remainingSeconds;
    private boolean isRunning = false;
    private final TimerListener listener;

    public interface TimerListener {
        void onTimerTick(int remainingSeconds);

        void onTimerComplete();
    }

    public TimerService(TimerListener listener) {
        this.listener = listener;
        this.timer = new javax.swing.Timer(1000, _ -> {
            remainingSeconds--;
            if (remainingSeconds >= 0) {
                listener.onTimerTick(remainingSeconds);
            }
            if (remainingSeconds <= 0) {
                stopTimer();
                listener.onTimerComplete();
            }
        });
    }

    public void startTimer(int minutes) {
        this.remainingSeconds = minutes * 60;
        this.isRunning = true;
        timer.start();
        listener.onTimerTick(remainingSeconds);
    }

    public void stopTimer() {
        timer.stop();
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
