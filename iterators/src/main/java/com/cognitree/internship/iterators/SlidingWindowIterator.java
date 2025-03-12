package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SlidingWindowIterator<T> implements Iterable<List<T>> {
    private final List<T> list;
    private final int windowSize;

    public SlidingWindowIterator(int windowSize, List<T> list) {
        this.windowSize = windowSize;
        this.list = list;
    }

    @Override
    public Iterator<List<T>> iterator() {
        return new Iterator<List<T>>() {
            private int currIndex = 0;

            @Override
            public boolean hasNext() {
                return currIndex <= list.size() - windowSize;
            }

            @Override
            public List<T> next() {
                List<T> subList = new ArrayList<>();
                for (int i = currIndex; i < currIndex + windowSize && i < list.size(); i++) {
                    subList.add(list.get(i));
                }
                currIndex++;
                return subList;
            }
        };
    }
}
