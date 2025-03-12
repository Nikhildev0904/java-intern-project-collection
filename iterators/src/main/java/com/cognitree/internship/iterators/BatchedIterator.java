package com.cognitree.internship.iterators;

import java.util.*;

public class BatchedIterator<T> implements Iterator<Collection<T>> {
    private final Iterator<T> iterator;
    private final int batchSize;

    public BatchedIterator(Collection<T> collection, int batchSize) {
        this.iterator = collection.iterator();
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        List<T> batch = new ArrayList<>(batchSize);
        int count = 0;
        while (iterator.hasNext() && count < batchSize) {
            batch.add(iterator.next());
            count++;
        }
        return batch;
    }
}
