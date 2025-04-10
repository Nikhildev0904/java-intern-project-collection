package com.cognitree.internship.infinite_stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class to implement CircularQueue
 *
 * @param <T> - Generic type
 */
public class CircularQueue<T> implements Iterable<T> {

    private final T[] array;

    private int index;
    private int length;

    /**
     * Constructor to initialise CircularQueue
     *
     * @param size - size of queue
     */
    public CircularQueue(int size) {
        this.array = (T[]) new Object[size];
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
        index = (index + 1) % array.length;
        if (length == array.length) {
            last = array[index];
        } else {
            length++;
        }
        array[index] = element;
        return last;
    }

    public int getLength() {
        return length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int head = ((index + 1) - length + array.length) % array.length;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("next() method called without any elements in the queue");
                }
                T value = array[head];
                head = (head + 1) % array.length;
                count++;
                return value;
            }
        };
    }
}
