package com.cognitree.internship.word_counter.sequential;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SequentialTest {

    @Test
    void testGetWordCount() {
        Sequential sequential = new Sequential();
        List<String> lines = List.of("This is a test", "This is a test", "This is a test", "This is a test", "This is a test");
        Map<String, Integer> actual = sequential.getWordCount(lines);
        Map<String, Integer> expected = Map.of("this", 5, "is", 5, "a", 5, "test", 5);
        assertEquals(expected, actual);
    }

    @Test
    void testGetWordCountEmptyList() {
        Sequential sequential = new Sequential();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordCountMap = sequential.getWordCount(lines);
        assertTrue(wordCountMap.isEmpty());
    }
}