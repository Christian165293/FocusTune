package org.example;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\nP = Play");
        System.out.println("S = Pause");
        System.out.println("N = Next Song");
        System.out.println("D = Dislike");
        System.out.println("R = Reset Song");
        System.out.println("EL = Enable Loop");
        System.out.println("DL = Disable Loop");
        System.out.println("Q = Quit");
        System.out.print("Enter your choice: ");
    }

    public String getInput() {
        return scanner.next().toUpperCase();
    }

    public void close() {
        scanner.close();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
