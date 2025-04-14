package com.cognitree.internship.word_counter.threads;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentRunnableThreadTest {

    @Test
    void testGetWordCount() throws InterruptedException {
        List<String> lines = Arrays.asList("hello world", "hello again", "world is nice");
        Map<String, Integer> expectedMap = Map.of("hello", 2, "world", 2, "again", 1, "is", 1, "nice", 1);
        ConcurrentRunnableThread concurrentRunnableThread = new ConcurrentRunnableThread();
        Map<String, Integer> actualMap = concurrentRunnableThread.getWordCount(lines);
        assertEquals(expectedMap, actualMap);
    }

    @Test
    void testGetWordCountEmptyList() throws InterruptedException {
        ConcurrentRunnableThread concurrentRunnableThread = new ConcurrentRunnableThread();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordCountMap = concurrentRunnableThread.getWordCount(lines);
        assertTrue(wordCountMap.isEmpty());
    }

    @Test
    void testGetWordCountNullList() throws InterruptedException {
        ConcurrentRunnableThread concurrentRunnableThread = new ConcurrentRunnableThread();
        List<String> lines = null;
        assertThrows(NullPointerException.class, () -> concurrentRunnableThread.getWordCount(lines));
    }
}