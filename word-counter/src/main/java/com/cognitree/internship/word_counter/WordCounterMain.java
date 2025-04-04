package com.cognitree.internship.word_counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordCounterMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WordCounterMain <inputFile>");
            return;
        }
        String inputFile = args[0];
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of(inputFile));
        } catch (IOException e) {
            System.out.println("Error parsing the input file");
            e.printStackTrace();
            return;
        }
        Comparison comparison = new Comparison();
        try {
            comparison.compareAll(lines);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
