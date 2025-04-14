package com.cognitree.internship.word_counter.futures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureTest {

    @Test
    void testGetWordCount() throws InterruptedException, ExecutionException {
        CompletableFuture completableFuture = new CompletableFuture();
        List<String> lines = List.of("This is a test", "This is a test", "This is a test", "This is a test", "This is a test");
        Map<String, Integer> actual = completableFuture.getWordCount(lines);
        Map<String, Integer> expected = Map.of("this", 5, "is", 5, "a", 5, "test", 5);
        assertEquals(expected, actual);
    }

    @Test
    void testGetWordCountEmptyList() throws InterruptedException, ExecutionException {
        CompletableFuture completableFuture = new CompletableFuture();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordCountMap = completableFuture.getWordCount(lines);
        assertTrue(wordCountMap.isEmpty());
    }
}