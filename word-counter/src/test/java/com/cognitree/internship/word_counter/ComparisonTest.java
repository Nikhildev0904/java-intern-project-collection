package com.cognitree.internship.word_counter;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonTest {

    @Test
    void testCompareResults() {
        Map<String, Integer> map1 = Map.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> map2 = Map.of("a", 1, "c", 3, "b", 2);
        assertTrue(Comparison.compareResults(map1, map2));
        Map<String, Integer> map3 = Map.of("a", 1, "b", 2, "c", 4);
        assertFalse(Comparison.compareResults(map1, map3));
    }
}