package com.cognitree.internship.word_counter.sequential;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.cognitree.internship.word_counter.TextFileParser.parseFile;

public class WordCounterMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WordCounterMain <inputFile>");
            return;
        }
        String inputFile = args[0];
        List<String> lines = null;
        try {
            lines = parseFile(inputFile);
        } catch (IOException e) {
            System.out.println("Error parsing the input file");
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> wordCountMap = wordCounter.getWordCount(lines);
        long end = System.currentTimeMillis();
        System.out.println("Time taken for Sequential Computation: " + (end - start));
    }
}
