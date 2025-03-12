package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BatchedIterator<T> implements Iterator<List<T>> {
    private final List<T> list;
    private final int batchSize;
    private int currentIndex = 0;

    public BatchedIterator(int batchSize, List<T> list) {
        this.batchSize = batchSize;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < list.size();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        List<T> subList = new ArrayList<>();
        int endIndex = currentIndex + batchSize;
        for (; currentIndex < endIndex && currentIndex < list.size(); currentIndex++) {
            subList.add(list.get(currentIndex));
        }
        return subList;
    }
}
