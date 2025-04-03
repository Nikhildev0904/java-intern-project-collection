package com.cognitree.internship.word_counter.forkjoinpool;

import com.cognitree.internship.word_counter.sequential.WordCounter;

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
            throw new RuntimeException(e);
        }

        /* -- Sequential Computation -- */
        long start = System.currentTimeMillis();
        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> wordCountMap = wordCounter.getWordCount(lines);
        long end = System.currentTimeMillis();
        System.out.println("Time taken for Sequential Computation: " + (end - start));

        /* -- Multithreading Using ForkJoin - Recursive Action -- */
        start = System.currentTimeMillis();
        ForkJoinWordCounter forkJoinWordCounter = new ForkJoinWordCounter();
        Map<String, Integer> forkJoinActionWordCount;
        try {
            forkJoinActionWordCount = forkJoinWordCounter.getWordCountWithForkJoinAction(lines);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for ForkJoin RecursiveAction: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, forkJoinActionWordCount));

        /* -- Multithreading Using ForkJoin - Recursive Task -- */
        start = System.currentTimeMillis();
        Map<String, Integer> forkJoinTaskWordCounter;
        try {
            forkJoinTaskWordCounter = forkJoinWordCounter.getWordCountWithForkJoinTask(lines);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for ForkJoin RecursiveTask: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, forkJoinTaskWordCounter));
    }

    private static boolean compareResults(Map<String, Integer> map1, Map<String, Integer> map2) {
        if (map1.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            String key = entry.getKey();
            Integer value1 = entry.getValue();
            Integer value2 = map2.get(key);
            if (!value1.equals(value2)) {
                return false;
            }
        }
        return true;
    }
}
