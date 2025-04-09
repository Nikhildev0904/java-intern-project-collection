package com.cognitree.internship.iterators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CircularQueue<T> implements Iterable<T> {
    private static final Logger logger = LoggerFactory.getLogger(CircularQueue.class);
    private final T[] array;
    private int index;
    private int length;

    public CircularQueue(int size) {
        this.array = (T[]) new Object[size];
        this.index = -1;
        this.length = 0;
        logger.info("CircularQueue initialized with size {}", size);
    }

    public final void slide(T element) {
        index = (index + 1) % array.length;
        if (length != array.length) {
            length++;
        }
        array[index] = element;
    }

    public int getLength() {
        return length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int head = ((index + 1) - length + array.length) % array.length;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < length;
            }

            @Override
            public T next() {
                T value = array[head];
                head = (head + 1) % array.length;
                count++;
                return value;
            }
        };
    }

    public List<T> getWindowElements() {
        List<T> result = new ArrayList<>(length);
        int start = (index + 1 - length + array.length) % array.length;
        for (int i = 0; i < length; i++) {
            result.add(array[(start + i) % array.length]);
        }
        return result;
    }
}
