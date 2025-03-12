package com.cognitree.internship.iterators;

import java.util.*;

public class SlidingWindowIterator<T> implements Iterator<Collection<T>> {
    private final List<T> list;
    private final int windowSize;
    private int currentIndex = 0;

    public SlidingWindowIterator(Collection<T> collection, int windowSize) {
        this.list = new ArrayList<>(collection);
        this.windowSize = windowSize;
    }

    @Override
    public boolean hasNext() {
        return currentIndex <= list.size() - windowSize;
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        List<T> window = new ArrayList<>();
        for (int i = currentIndex; i < currentIndex + windowSize && i < list.size(); i++) {
            window.add(list.get(i));
        }
        currentIndex++;
        return window;
    }

}
