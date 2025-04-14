package com.cognitree.internship.word_counter.threadpools;

import com.cognitree.internship.word_counter.threads.CallableThread;
import com.cognitree.internship.word_counter.threads.ConcurrentRunnableThread;
import com.cognitree.internship.word_counter.threads.SyncRunnableThread;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

    @Test
    void testGetWordCountEmptyList() throws InterruptedException, ExecutionException {
        CallableThreadPool callableThreadPool = new CallableThreadPool();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordCountMap = callableThreadPool.getWordCount(lines);
        assertTrue(wordCountMap.isEmpty());
    }
}