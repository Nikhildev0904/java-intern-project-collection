package com.cognitree.internship.infinite_stream;

/**
 * Class to implement immutable CircularQueue
 * @param <T> - Generic type
 */
public class CircularQueue<T extends Number> {
    private int front;
    private int rear;
    private final T[] nums;
    private final int capacity;
    private int length;

    /**
     * Constructor to initialise CircularQueue
     * @param size - size of queue
     */
    CircularQueue(int size){
       this.nums = (T[]) new Number[size];
       this.front = 0;
       this.length = 0;
       this.capacity = size;
       this.length = 0;
    }

    /**
     * Function to insert numbers into the queue
     * @param element - number to be inserted
     */
    public final T enqueue(T element){
        T last = null;

        if (length == capacity) {
            last = nums[front];
            nums[front] = element;

            front = (front + 1) % capacity;
            rear = (rear + 1) % capacity;
        } else {
            nums[rear] = element;
            rear = (rear + 1) % capacity;
            length++;
        }
        return last;

    }

    public int getLength() {
        return length;
    }

}
