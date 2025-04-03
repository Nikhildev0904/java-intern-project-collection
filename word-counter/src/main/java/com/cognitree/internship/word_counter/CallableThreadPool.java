package com.cognitree.internship.word_counter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class CallableThreadPool {

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException, ExecutionException {
        Map<String, Integer> wordCountMap = new HashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        List<Callable<Map<String, Integer>>> tasks = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            tasks.add(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines, start, end);
            });
        }
        List<Future<Map<String, Integer>>> futures = executorService.invokeAll(tasks);
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> localMap = future.get();
            localMap.forEach((word, count) -> wordCountMap.merge(word, count, Integer::sum));
        }
        executorService.shutdown();
        return wordCountMap;
    }
}
