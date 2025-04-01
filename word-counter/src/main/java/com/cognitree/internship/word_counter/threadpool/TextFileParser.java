package com.cognitree.internship.word_counter.threadpool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextFileParser {

    public static List<String> parseFile(String inputFile) throws IOException {
        return Files.readAllLines(Path.of(inputFile));
    }
}
