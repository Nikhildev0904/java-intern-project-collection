package com.cognitree.internship.word_counter.threadpools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class CallableThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(CallableThreadPool.class);

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException, ExecutionException {
        Map<String, Integer> wordCountMap = new HashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        logger.info("Starting {} word counter with {} threads", this.getClass().getSimpleName(), numThreads);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            futures.add(executorService.submit(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines, start, end);
            }));
        }
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> localMap = future.get();
            localMap.forEach((word, count) -> wordCountMap.merge(word, count, Integer::sum));
        }
        executorService.shutdown();
        logger.info("All threads finished computation. Total unique words: {}", wordCountMap.size());
        return wordCountMap;
    }
}
