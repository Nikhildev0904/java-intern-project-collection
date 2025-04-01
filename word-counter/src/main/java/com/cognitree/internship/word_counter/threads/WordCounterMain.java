package com.cognitree.internship.word_counter.threads;

import java.io.IOException;

public class WordCounterMain {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordCounterMain <inputFile> <outputDir>");
            return;
        }
        String inputFile = args[0];
        String outputDir = args[1];

        /* -- Multithreading Using Callable */
        long start = System.currentTimeMillis();
        RunnableWordCounter runnableWordCounter = new RunnableWordCounter();
        try {
            runnableWordCounter.getWordCount(inputFile, outputDir);
        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken for Runnable: " + (end - start));

        /* -- Multithreading Using Callable */
        start = System.currentTimeMillis();
        CallableWordCounter callableWordCounter = new CallableWordCounter();
        try {
            callableWordCounter.getWordCount(inputFile, outputDir);
        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Callable: " + (end - start));
    }
}
