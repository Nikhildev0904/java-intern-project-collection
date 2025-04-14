package com.cognitree.internship.word_counter.threadpools;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RunnableThreadPoolTest {
    private static final Logger logger = LoggerFactory.getLogger(RunnableThreadPoolTest.class);

    @Test
    void testGetWordCount() throws InterruptedException {
        List<String> lines = Arrays.asList("hello world", "hello again", "world is nice");
        Map<String, Integer> expectedMap = Map.of("hello", 2, "world", 2, "again", 1, "is", 1, "nice", 1);
        RunnableThreadPool runnableThreadPool = new RunnableThreadPool();
        Map<String, Integer> actualMap = runnableThreadPool.getWordCount(lines);
        assertEquals(expectedMap, actualMap);
    }

    @Test
    void testGetWordCountEmptyList() throws InterruptedException {
        List<String> lines = List.of();
        Map<String, Integer> expectedMap = Map.of();
        RunnableThreadPool runnableThreadPool = new RunnableThreadPool();
        Map<String, Integer> actualMap = runnableThreadPool.getWordCount(lines);
        assertEquals(expectedMap, actualMap);
    }
}