package com.cognitree.internship.iterators;

import java.util.Iterator;

public class CircularQueue<T> implements Iterable<T> {
    private final T[] array;
    private int index;
    private int length;

    public CircularQueue(int size) {
        this.array = (T[]) new Object[size];
        this.index = -1;
        this.length = 0;
    }

    public final T slide(T element) {
        T last = null;
        index = (index + 1) % array.length;
        if (length != array.length) {
            length++;
        } else {
            last = array[index];
        }
        array[index] = element;
        return last;
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
}
