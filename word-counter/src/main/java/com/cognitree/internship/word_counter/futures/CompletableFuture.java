package com.cognitree.internship.word_counter.futures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class CompletableFuture {

    private static final Logger logger = LoggerFactory.getLogger(CompletableFuture.class);

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException, ExecutionException {
        Map<String, Integer> sharedMap = new HashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        logger.info("Starting {} word counter with {} threads", this.getClass().getSimpleName(), numThreads);
        List<java.util.concurrent.CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            java.util.concurrent.CompletableFuture<Void> completableFuture = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines, start, end);
            }, executorService).thenAccept(localMap -> {
                localMap.forEach((word, count) -> {
                    synchronized (sharedMap) {
                        sharedMap.merge(word, count, Integer::sum);
                    }
                });
            });
            futures.add(completableFuture);
        }
        java.util.concurrent.CompletableFuture<Void> allFutures = java.util.concurrent.CompletableFuture.allOf(
                futures.toArray(new java.util.concurrent.CompletableFuture[0])
        );
        allFutures.get();
        executorService.shutdown();
        logger.info("All threads finished Computation. Total unique words: {}", sharedMap.size());
        return sharedMap;
    }
}
