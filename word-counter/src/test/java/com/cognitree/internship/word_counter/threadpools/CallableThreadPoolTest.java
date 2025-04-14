package com.cognitree.internship.word_counter.threadpools;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CallableThreadPoolTest {

    @Test
    void testGetWordCount() throws InterruptedException, ExecutionException {
        CallableThreadPool callableThreadPool = new CallableThreadPool();
        List<String> lines = List.of("a", "a", "b", "c", "d", "e");
        Map<String, Integer> wordCountMap = callableThreadPool.getWordCount(lines);
        assertEquals(2, wordCountMap.get("a"));
        assertEquals(1, wordCountMap.get("b"));
        assertEquals(1, wordCountMap.get("c"));
        assertEquals(1, wordCountMap.get("d"));
        assertEquals(1, wordCountMap.get("e"));
    }

}