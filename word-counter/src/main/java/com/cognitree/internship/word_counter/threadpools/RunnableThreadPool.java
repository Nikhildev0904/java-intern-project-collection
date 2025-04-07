package com.cognitree.internship.word_counter.threadpools;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class RunnableThreadPool {

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException {
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
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
        return sharedMap;
    }
}
