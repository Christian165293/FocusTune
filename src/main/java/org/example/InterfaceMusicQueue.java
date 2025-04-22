package org.example;

// Interface for queue operations
public interface InterfaceMusicQueue {
    boolean isEmpty();

    String peek();

    void add(String data);

    void remove();

    int getSize();
}
