package com.cognitree.internship.word_counter.threads;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;


public class ConcurrentRunnableThread {

    public Map<String, Integer> getWordCount(List<String> lines) throws InterruptedException {
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                Map<String, Integer> localCounts = processLines(lines, start, end);
                for (Map.Entry<String, Integer> entry : localCounts.entrySet()) {
                    sharedMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
        return sharedMap;
    }
}
