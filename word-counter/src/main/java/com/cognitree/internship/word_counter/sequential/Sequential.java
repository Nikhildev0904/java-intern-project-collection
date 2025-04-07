package com.cognitree.internship.word_counter.sequential;

import java.util.List;
import java.util.Map;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class Sequential {

    public Map<String, Integer> getWordCount(List<String> lines) {
        return processLines(lines, 0, lines.size());
    }
}
