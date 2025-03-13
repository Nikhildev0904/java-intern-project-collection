package com.cognitree.internship.iterators;

import java.util.*;

public class SlidingWindowIterator<T> implements Iterator<List<T>> {
    private final Iterator<T> iterator;
    private final CircularQueue<T> window;
    private final int windowSize;
    private boolean filled = false;

    public SlidingWindowIterator(Iterable<T> iterable, int windowSize) {
        this.iterator = iterable.iterator();
        this.window = new CircularQueue<>(windowSize);
        this.windowSize = windowSize;
    }

    @Override
    public boolean hasNext() {
        return filled || iterator.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No elements left");
        }
        while (!filled && iterator.hasNext()) {
            window.slide(iterator.next());
            if (window.getLength() == windowSize) {
                filled = true;
            }
        }
        if (!filled && hasNext()) {
            throw new NoSuchElementException("No more windows available");
        }
        List<T> list = new ArrayList<>();
        for (T item : window) {
            list.add(item);
        }
        if (iterator.hasNext()) {
            window.slide(iterator.next());
        } else {
            filled = false;
        }
        return list;
    }
}
