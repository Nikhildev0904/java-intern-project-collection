package com.cognitree.internship.word_counter.threads;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.cognitree.internship.word_counter.threads.TextFileParser.parseFile;

public class CallableWordCounter {

    public void getWordCount(String inputFile, String outputDir) throws IOException, InterruptedException, ExecutionException {
        List<String> lines = parseFile(inputFile);
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        List<FutureTask<Map<String, Integer>>> tasks = new ArrayList<>();
        Thread[] threads = new Thread[numThreads];
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            Callable<Map<String, Integer>> callable = () -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                return processLines(lines.subList(start, end));
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
            localMap.forEach((word, count) -> sharedMap.merge(word, count, Integer::sum));
        }
        writeReport(outputDir, sharedMap);
    }

    private static void writeReport(String outputDir, Map<String, Integer> sharedMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(String.valueOf(Paths.get(outputDir, "word_count_threads_callable.csv")))))) {
            for (Map.Entry<String, Integer> entry : sharedMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + "," + value);
                writer.newLine();
            }
        }
    }

    private static Map<String, Integer> processLines(List<String> lines) {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String line : lines) {
            String[] words = line.toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }
        return wordCounts;
    }
}
