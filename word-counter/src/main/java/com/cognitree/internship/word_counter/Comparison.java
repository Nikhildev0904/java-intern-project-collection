package com.cognitree.internship.word_counter;

public class Comparison {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WordCounterMain <inputFile>");
            return;
        }
        String inputFile = args[0];
        com.cognitree.internship.word_counter.threads.WordCounterMain.main(new String[]{inputFile});
        System.out.println();
        com.cognitree.internship.word_counter.threadpool.WordCounterMain.main(new String[]{inputFile});
        System.out.println();
        com.cognitree.internship.word_counter.futures.WordCounterMain.main(new String[]{inputFile});
        System.out.println();
        com.cognitree.internship.word_counter.forkjoinpool.WordCounterMain.main(new String[]{inputFile});
    }
}
