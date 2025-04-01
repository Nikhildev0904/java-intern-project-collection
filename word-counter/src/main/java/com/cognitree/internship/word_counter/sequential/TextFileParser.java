package com.cognitree.internship.word_counter.sequential;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TextFileParser {

    public void parseFile(String inputFile, Consumer<String> consumer) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(inputFile))) {
            lines.map(line -> line.toLowerCase().split("\\W+"))
                    .forEach(strings -> Arrays.stream(strings)
                            .forEach(word -> {
                                if (!word.isEmpty())
                                    consumer.accept(word);
                            }));
        }
    }
}
