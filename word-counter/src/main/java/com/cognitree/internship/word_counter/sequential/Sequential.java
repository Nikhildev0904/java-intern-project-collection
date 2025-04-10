package com.cognitree.internship.word_counter.sequential;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class Sequential {

    private static final Logger logger = LoggerFactory.getLogger(Sequential.class);

    public Map<String, Integer> getWordCount(List<String> lines) {
        logger.info("Starting {} word counter", this.getClass().getSimpleName());
        Map<String, Integer> wordCountMap = processLines(lines, 0, lines.size());
        logger.info("Finished Computation. Total unique words: {}", wordCountMap.size());
        return wordCountMap;
    }
}
