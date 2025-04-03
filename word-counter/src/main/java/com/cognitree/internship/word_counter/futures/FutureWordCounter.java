package com.cognitree.internship.word_counter.futures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;


public class FutureWordCounter {

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException, ExecutionException {
        Map<String, Integer> sharedMap = new HashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines.subList(start, end));
            }, executorService).thenAccept(localMap -> {
                localMap.forEach((word, count) -> {
                    synchronized (sharedMap) {
                        sharedMap.merge(word, count, Integer::sum);
                    }
                });
            });
            futures.add(completableFuture);
        }
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );
        allFutures.get();
        executorService.shutdown();
        return sharedMap;
    }
}
