package com.cognitree.internship.word_counter.threads;

import com.cognitree.internship.word_counter.sequential.WordCounter;
import com.cognitree.internship.word_counter.threads.concurrent_ds.RunnableWordCounter;
import com.cognitree.internship.word_counter.threads.synchronized_blocks.SyncRunnableWordCounter;

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
            return;
        }

        /* -- Sequential Computation -- */
        long start = System.currentTimeMillis();
        WordCounter wordCounter = new WordCounter();
        Map<String, Integer> wordCountMap = wordCounter.getWordCount(lines);
        long end = System.currentTimeMillis();
        System.out.println("Time taken for Sequential Computation: " + (end - start));

        /* -- Multithreading using threads - runnable with concurrent data structure */
        start = System.currentTimeMillis();
        RunnableWordCounter runnableWordCounter = new RunnableWordCounter();
        Map<String, Integer> runnableConcDSWordCount;
        try {
            runnableConcDSWordCount = runnableWordCounter.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Runnable threads using concurrent data structure: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, runnableConcDSWordCount));


        /* -- Multithreading using threads - runnable with synchronized block */
        start = System.currentTimeMillis();
        SyncRunnableWordCounter syncRunnableWordCounter = new SyncRunnableWordCounter();
        Map<String, Integer> runnableSyncBlockWordCount;
        try {
            runnableSyncBlockWordCount = syncRunnableWordCounter.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Runnable threads with synchronized block: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, runnableSyncBlockWordCount));

        /* -- Multithreading using threads - callable */
        start = System.currentTimeMillis();
        CallableWordCounter callableWordCounter = new CallableWordCounter();
        Map<String, Integer> callableWordCounterWordCount;
        try {
            callableWordCounterWordCount = callableWordCounter.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Callable threads: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, callableWordCounterWordCount));
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
