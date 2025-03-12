package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BatchedIterator<T> implements Iterable<List<T>> {
    private final List<T> list;
    private final int batchSize;

    public BatchedIterator(int batchSize, List<T> list) {
        this.batchSize = batchSize;
        this.list = list;
    }

    @Override
    public Iterator<List<T>> iterator() {
        return new Iterator<>() {
            private int currIndex = 0;

            @Override
            public boolean hasNext() {
                return currIndex < list.size();
            }

            @Override
            public List<T> next() {
                List<T> subList = new ArrayList<>();
                int size = currIndex + batchSize;
                for (; currIndex < size && currIndex < list.size(); currIndex++) {
                    subList.add(list.get(currIndex));
                }
                return subList;
            }
        };
    }
}
