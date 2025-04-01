package com.cognitree.internship.word_counter.sequential;

import java.io.IOException;

public class WordCounterMain {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordCounterMain <inputFile> <outputDir>");
            return;
        }
        String inputFile = args[0];
        String outputDir = args[1];
        long start = System.currentTimeMillis();
        WordCounter wordCounter = new WordCounter();
        try {
            wordCounter.getWordCount(inputFile, outputDir);
        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
    }
}
