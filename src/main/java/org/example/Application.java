package org.example;

//Entry point that connects all the components
public class Application {
    private final UserControls userControls;

    public Application(UserControls userControls) {
        this.userControls = userControls;
    }

    public void run() {
        userControls.setVisible(true);
    }
}
