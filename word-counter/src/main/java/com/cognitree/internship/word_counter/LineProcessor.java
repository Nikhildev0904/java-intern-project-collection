package com.cognitree.internship.word_counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineProcessor {

    public static Map<String, Integer> processLines(List<String> lines, int start, int end) {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (int i = start; i < end; i++) {
            String line = lines.get(i);
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
