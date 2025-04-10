package com.cognitree.internship.word_counter.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ForkJoin {

    private static final Logger logger = LoggerFactory.getLogger(ForkJoin.class);

    public Map<String, Integer> getWordCountWithForkJoinTask(List<String> lines) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        logger.info("Starting {} word counter with 8 parallelism", WordCountRecursiveTask.class.getSimpleName());
        WordCountRecursiveTask task = new WordCountRecursiveTask(lines, 0, lines.size());
        Map<String, Integer> sharedMap = forkJoinPool.invoke(task);
        logger.info("All subtasks finished. ForkJoin Recursive Task completed. Total unique words: {}", sharedMap.size());
        return sharedMap;
    }

    public Map<String, Integer> getWordCountWithForkJoinAction(List<String> lines) {
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        logger.info("Starting {} word counter with 8 parallelism", WordCountRecursiveAction.class.getSimpleName());
        WordCountRecursiveAction action = new WordCountRecursiveAction(lines, 0, lines.size(), sharedMap);
        forkJoinPool.invoke(action);
        logger.info("All subtasks finished. ForkJoin Recursive Action completed. Total unique words: {}", sharedMap.size());
        return sharedMap;
    }
}
