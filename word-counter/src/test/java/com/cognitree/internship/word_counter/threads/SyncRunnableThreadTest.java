package com.cognitree.internship.word_counter.threads;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SyncRunnableThreadTest {

    @Test
    void testGetWordCount() throws InterruptedException {
        List<String> lines = Arrays.asList("hello world", "hello again", "world is nice");
        Map<String, Integer> expectedMap = Map.of("hello", 2, "world", 2, "again", 1, "is", 1, "nice", 1);
        SyncRunnableThread syncRunnableThread = new SyncRunnableThread();
        Map<String, Integer> actualMap = syncRunnableThread.getWordCount(lines);
        assertEquals(expectedMap, actualMap);
    }

    @Test
    void testGetWordCountEmptyList() throws InterruptedException {
        List<String> lines = new ArrayList<>();
        Map<String, Integer> expectedMap = Map.of();
        SyncRunnableThread syncRunnableThread = new SyncRunnableThread();
        Map<String, Integer> actualMap = syncRunnableThread.getWordCount(lines);
        assertTrue((actualMap).isEmpty());
    }

    @Test
    void testGetWordCountNullList() {
        SyncRunnableThread syncRunnableThread = new SyncRunnableThread();
        List<String> lines = null;
        assertThrows(NullPointerException.class, () -> syncRunnableThread.getWordCount(lines));
    }
}
