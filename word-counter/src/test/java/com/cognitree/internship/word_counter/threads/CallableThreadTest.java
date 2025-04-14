package com.cognitree.internship.word_counter.threads;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CallableThreadTest {
    @Test
    void testGetWordCount() throws InterruptedException, ExecutionException {
        CallableThread callableThread = new CallableThread();
        List<String> lines = List.of("a", "a", "b", "c", "d", "e");
        Map<String, Integer> wordCountMap = callableThread.getWordCount(lines);
        assertEquals(2, wordCountMap.get("a"));
        assertEquals(1, wordCountMap.get("b"));
        assertEquals(1, wordCountMap.get("c"));
        assertEquals(1, wordCountMap.get("d"));
        assertEquals(1, wordCountMap.get("e"));
    }

    @Test
    void testGetWordCountEmptyList() throws InterruptedException, ExecutionException {
        CallableThread callableThread = new CallableThread();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordCountMap = callableThread.getWordCount(lines);
        assertTrue(wordCountMap.isEmpty());
    }

    @Test
    void testGetWordCountNullList() throws InterruptedException, ExecutionException {
        CallableThread callableThread = new CallableThread();
        List<String> lines = null;
        assertThrows(NullPointerException.class, () -> callableThread.getWordCount(lines));
    }
}