package org.example;

//Queue created using linked list that stores file paths to songs
public class MusicQueue implements InterfaceMusicQueue {
    private static class Node {
        private final String data; // Value stored in the node
        private Node next; // Pointer to the next node

        // Constructor initializes the node with data
        private Node(String data) {
            this.data = data;
        }
    }

    private Node head; // Pointer to the front of the queue
    private Node tail; // Pointer to the back of the queue
    private int size;

    // Method to check if the queue is empty
    @Override
    public boolean isEmpty() {
        return (head == null);
    }

    // Method to return the front element of the queue
    @Override
    public String peek() {
        return head.data;
    }

    // Method to add an element to the back of the queue
    @Override
    public void add(String data) {
        Node node = new Node(data);
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        if (head == null) {
            head = node;
        }
        size++;
    }

    // Method to remove and return the front element of the queue
    @Override
    public void remove() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
    }

    @Override
    public int getSize() {
        return size;
    }
}
