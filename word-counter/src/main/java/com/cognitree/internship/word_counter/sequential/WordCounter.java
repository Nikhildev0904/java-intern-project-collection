package com.cognitree.internship.word_counter.sequential;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class WordCounter {

    public Map<String, Integer> getWordCount(List<String> lines) {
        Map<String, Integer> wordCountMap = processLines(lines);
        return wordCountMap;
    }
}
