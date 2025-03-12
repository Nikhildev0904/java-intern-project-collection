package com.cognitree.internship.iterators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IteratorExample {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Iterable<List<Integer>> numbersIterator = new SlidingWindowIterator<>(3, numbers);
        System.out.println("Sliding Window Iteration: ");
        for (List<Integer> integers : numbersIterator) {
            System.out.print(integers + " ");
        }
        System.out.println();
        numbersIterator = new BatchedIterator<>(3, numbers);
        System.out.println("Batched Iteration: ");
        for (List<Integer> integers : numbersIterator) {
            System.out.print(integers + " ");
        }
        List<String> words = new ArrayList<>(Arrays.asList("hello", "who", "he", "dog", "cat", "parrot", "lion"));
        Iterable<List<String>> wordsIterator = new SlidingWindowIterator<>(4, words);
        System.out.println("Sliding Window Iteration: ");
        for (List<String> strings : wordsIterator) {
            System.out.print(strings + " ");
        }
        System.out.println();
        wordsIterator = new BatchedIterator<>(4, words);
        System.out.println("Batched Iteration: ");
        for (List<String> strings : wordsIterator) {
            System.out.print(strings + " ");
        }
    }
}
