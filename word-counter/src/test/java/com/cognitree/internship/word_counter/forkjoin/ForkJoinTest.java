package com.cognitree.internship.word_counter.forkjoin;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ForkJoinTest {

    @Test
    void testGetWordCountWithForkJoinTask() {
        ForkJoin forkJoin = new ForkJoin();
        List<String> lines = List.of("This is a test", "This is a test", "This is a test", "This is a test", "This is a test");
        Map<String, Integer> actual = forkJoin.getWordCountWithForkJoinTask(lines);
        Map<String, Integer> expected = Map.of("this", 5, "is", 5, "a", 5, "test", 5);
        assertEquals(expected, actual);
    }

    @Test
    void testGetWordCountWithForkJoinAction() {
        ForkJoin forkJoin = new ForkJoin();
        List<String> lines = List.of("This is a test", "This is a test", "This is a test", "This is a test", "This is a test");
        Map<String, Integer> actual = forkJoin.getWordCountWithForkJoinAction(lines);
        Map<String, Integer> expected = Map.of("this", 5, "is", 5, "a", 5, "test", 5);
        assertEquals(expected, actual);
    }
}