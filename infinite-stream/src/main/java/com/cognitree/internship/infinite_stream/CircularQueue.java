package com.cognitree.internship.infinite_stream;

/**
 * Class to implement CircularQueue
 *
 * @param <T> - Generic type
 */
public class CircularQueue<T> {
    private final T[] nums;
    private int index;
    private int length;

    /**
     * Constructor to initialise CircularQueue
     *
     * @param size - size of queue
     */
    public CircularQueue(int size) {
        this.nums = (T[]) new Object[size];
        this.index = -1;
        this.length = 0;
    }

    /**
     * Function to insert numbers into the queue
     *
     * @param element - number to be inserted
     */
    public final T enqueue(T element) {
        T last = null;
        index = (index + 1) % nums.length;
        if (length == nums.length) {
            last = nums[index];
        } else {
            length++;
        }
        nums[index] = element;
        return last;
    }

    public int getLength() {
        return length;
    }

}
