package com.cognitree.internship.iterators;

import java.util.*;

public class IteratorExample {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Set<Integer> set = new LinkedHashSet<>(numbers);
        set.add(12);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(set, 3);
        System.out.println("Sliding Window Iteration:");
        while (slidingWindowIterator.hasNext()) {
            System.out.print(slidingWindowIterator.next() + " ");
        }
        System.out.println();
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(set, 3);
        System.out.println("Batched Iteration:");
        while (batchedIterator.hasNext()) {
            System.out.print(batchedIterator.next() + " ");
        }
    }
}
