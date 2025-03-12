package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorExample {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Iterator<List<Integer>> slidingWindowIterator = new SlidingWindowIterator<>(3, numbers);
        System.out.println("Sliding Window Iteration:");
        while (slidingWindowIterator.hasNext()) {
            System.out.print(slidingWindowIterator.next() + " ");
        }
        System.out.println();
        Iterator<List<Integer>> batchedIterator = new BatchedIterator<>(3, numbers);
        System.out.println("Batched Iteration:");
        while (batchedIterator.hasNext()) {
            System.out.print(batchedIterator.next() + " ");
        }
    }
}
