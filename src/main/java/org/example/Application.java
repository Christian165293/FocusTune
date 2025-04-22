package org.example;

public class Application {
    private final UserControls userControls;

    public Application(AudioPlaybackController playbackController,
                       SongQueueNavigator queueNavigator,
                       LoopManager loopManager,
                       AlarmPlayer alarmPlayer,
                       MusicRatings musicRatings) {
        this.userControls = new UserControls(
                playbackController,
                queueNavigator,
                loopManager,
                alarmPlayer,
                musicRatings
        );
    }

    public void run() {
        userControls.setVisible(true);
    }
}
