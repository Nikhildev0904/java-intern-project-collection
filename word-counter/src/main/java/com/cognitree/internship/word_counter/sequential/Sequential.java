package com.cognitree.internship.word_counter.sequential;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class Sequential {

    private static final Logger logger = LoggerFactory.getLogger(Sequential.class);

    public Map<String, Integer> getWordCount(List<String> lines) {
        logger.info("Starting {} word counter",this.getClass().getSimpleName());
        return processLines(lines, 0, lines.size());
    }
}
