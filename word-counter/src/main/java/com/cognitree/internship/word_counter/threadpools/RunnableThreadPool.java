package com.cognitree.internship.word_counter.threadpools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class RunnableThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(RunnableThreadPool.class);

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException {
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        logger.info("Starting {} word counter with {} threads",this.getClass().getSimpleName(), numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            executorService.execute(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                Map<String, Integer> localCounts = processLines(lines, start, end);
                for (Map.Entry<String, Integer> entry : localCounts.entrySet()) {
                    sharedMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        logger.info("All threads finished. Total unique words: {}", sharedMap.size());
        return sharedMap;
    }
}
