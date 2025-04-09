package com.cognitree.internship.word_counter.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class CallableThread {

    private static final Logger logger = LoggerFactory.getLogger(CallableThread.class);

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException, ExecutionException {
        Map<String, Integer> wordCountMap = new HashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        List<FutureTask<Map<String, Integer>>> tasks = new ArrayList<>();
        Thread[] threads = new Thread[numThreads];
        logger.info("Starting {} word counter with {} threads",this.getClass().getSimpleName(), numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            Callable<Map<String, Integer>> callable = () -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines, start, end);
            };
            FutureTask<Map<String, Integer>> task = new FutureTask<>(callable);
            tasks.add(task);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        for (FutureTask<Map<String, Integer>> task : tasks) {
            Map<String, Integer> localMap = task.get();
            localMap.forEach((word, count) -> wordCountMap.merge(word, count, Integer::sum));
        }
        logger.info("All threads finished. Total unique words: {}", wordCountMap.size());
        return wordCountMap;
    }
}
