package com.cognitree.internship.iterators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BatchedIterator<T> implements Iterator<List<T>> {

    private static final Logger logger = LoggerFactory.getLogger(BatchedIterator.class);

    private final Iterator<T> iterator;
    private final int batchSize;

    public BatchedIterator(Iterable<T> iterable, int batchSize) {
        logger.info("Initializing BatchedIterator with batch size: {}", batchSize);
        this.iterator = iterable.iterator();
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("next() called but no more elements available");
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
