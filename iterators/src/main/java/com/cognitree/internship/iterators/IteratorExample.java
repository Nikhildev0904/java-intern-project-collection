package com.cognitree.internship.iterators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class IteratorExample {
    private static final Logger logger = LoggerFactory.getLogger(IteratorExample.class);

    public static void main(String[] args) {
        logger.info("IteratorExample Application started");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Set<Integer> setOfNumbers = new LinkedHashSet<>(numbers);
        setOfNumbers.add(12);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(setOfNumbers, 3);
        logger.info("Starting Sliding window Iteration:");
        while (slidingWindowIterator.hasNext()) {
            System.out.print(slidingWindowIterator.next() + " ");
        }
        logger.info("Sliding window iteration complete");
        System.out.println();
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(setOfNumbers, 3);
        logger.info("Starting Batched Iteration:");
        while (batchedIterator.hasNext()) {
            System.out.print(batchedIterator.next() + " ");
        }
        logger.info("Batched iteration complete");
    }
}
