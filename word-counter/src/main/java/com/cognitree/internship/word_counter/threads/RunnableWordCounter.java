package com.cognitree.internship.word_counter.threads;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.cognitree.internship.word_counter.threads.TextFileParser.parseFile;


public class RunnableWordCounter {

    public void getWordCount(String inputFile, String outputDir) throws IOException, InterruptedException {
        List<String> lines = parseFile(inputFile);
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int linesPerThread = (lines.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                int start = threadIndex * linesPerThread;
                int end = Math.min(start + linesPerThread, lines.size());
                List<String> linesToProcess = lines.subList(start, end);
                Map<String, Integer> localCounts = processLines(linesToProcess);
                for (Map.Entry<String, Integer> entry : localCounts.entrySet()) {
                    sharedMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
        writeReport(outputDir, sharedMap);
    }

    private static void writeReport(String outputDir, Map<String, Integer> sharedMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(String.valueOf(Paths.get(outputDir, "word_count_threads_runnable.csv")))))) {
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
