package com.cognitree.internship.iterators;

import java.util.*;

public class SlidingWindowIterator<T> implements Iterator<List<T>> {
    private final Iterator<T> iterator;
    private final CircularQueue<T> window;
    private final int windowSize;
    private boolean isWindowFilled = false;

    public SlidingWindowIterator(Iterable<T> iterable, int windowSize) {
        this.iterator = iterable.iterator();
        this.window = new CircularQueue<>(windowSize);
        this.windowSize = windowSize;
    }

    @Override
    public boolean hasNext() {
        return isWindowFilled || iterator.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No elements left");
        }
        while (window.getLength() < windowSize && iterator.hasNext()) {
            window.slide(iterator.next());
        }
        isWindowFilled = window.getLength() == windowSize;
        if (!isWindowFilled) {
            throw new NoSuchElementException("No more windows available");
        }
        List<T> batch = window.getWindowElements();
        if (iterator.hasNext()) {
            window.slide(iterator.next());
        } else {
            isWindowFilled = false;
        }
        return batch;
    }
}
