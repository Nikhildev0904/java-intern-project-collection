package com.cognitree.internship.word_counter;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineProcessorTest {
    @Test
    void testProcessLines() {
        List<String> lines = List.of(
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog",
                "The quick brown fox jumps over the lazy dog"
        );
        Map<String, Integer> expected = Map.of(
                "the", 18,
                "quick", 9,
                "brown", 9,
                "fox", 9,
                "jumps", 9,
                "over", 9,
                "lazy", 9,
                "dog", 9
        );
        Map<String, Integer> result = LineProcessor.processLines(lines, 0, lines.size());
        assertEquals(expected, result);
    }
}