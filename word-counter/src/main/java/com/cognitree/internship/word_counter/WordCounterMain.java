package com.cognitree.internship.word_counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordCounterMain {

    private static final Logger logger = LoggerFactory.getLogger(WordCounterMain.class);

    public static void main(String[] args) {
        logger.info("Word counter application started");
        if (args.length < 1) {
            logger.error("No input file provided.");
            System.out.println("Usage: java WordCounterMain <inputFile>");
            return;
        }
        String inputFile = args[0];
        logger.info("Reading input file: {}", inputFile);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of(inputFile));
        } catch (IOException e) {
            logger.error("Failed to read file: {}", inputFile, e);
            return;
        }
        Comparison comparison = new Comparison();
        try {
            logger.info("Starting comparison of all concurrency methods...");
            comparison.compareAll(lines);
            logger.info("Comparison completed successfully.");
        } catch (InterruptedException | ExecutionException e) {
            logger.info("Unexpected error: {}", e.getMessage());
        }
    }
}
