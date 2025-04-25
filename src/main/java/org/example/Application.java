package org.example;

//Entry point that connects all the components of the application
public class Application {
    private final UserControls userControls;

    //the user controls is the UI component for user interaction
    public Application(UserControls userControls) {
        this.userControls = userControls;
    }

    //start the application by making the UI visible
    public void run() {
        userControls.setVisible(true);
    }
}
