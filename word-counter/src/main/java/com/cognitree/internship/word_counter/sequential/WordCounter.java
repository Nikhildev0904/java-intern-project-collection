package com.cognitree.internship.word_counter.sequential;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WordCounter {

    public void getWordCount(String inputFile, String outputDir) throws IOException {
        Map<String, Integer> wordContMap = new HashMap<>();
        new TextFileParser().parseFile(inputFile, word -> {
            wordContMap.put(word, wordContMap.getOrDefault(word, 0) + 1);
        });
        writeReport(outputDir, wordContMap);
    }

    private static void writeReport(String outputDir, Map<String, Integer> wordContMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(String.valueOf(Paths.get(outputDir, "word_count.csv")))))) {
            for (Map.Entry<String, Integer> entry : wordContMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                writer.write(key + "," + value);
                writer.newLine();
            }
        }
    }
}
