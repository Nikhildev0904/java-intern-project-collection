package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SlidingWindowIterator<T> implements Iterator<List<T>> {
    private final List<T> list;
    private final int windowSize;
    private int currentIndex = 0;

    public SlidingWindowIterator(int windowSize, List<T> list) {
        this.windowSize = windowSize;
        this.list = list;
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
        List<T> subList = new ArrayList<>();
        for (int i = currentIndex; i < currentIndex + windowSize && i < list.size(); i++) {
            subList.add(list.get(i));
        }
        currentIndex++;
        return subList;
    }

}
