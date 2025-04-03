package com.cognitree.internship.word_counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineProcessor {

    public static Map<String, Integer> processLines(List<String> lines) {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String line : lines) {
            String[] words = line.toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }
        return wordCounts;
    }
}
