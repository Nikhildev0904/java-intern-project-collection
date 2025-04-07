package com.cognitree.internship.word_counter.forkjoin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class WordCountRecursiveTask extends RecursiveTask<Map<String, Integer>> {
    private static final int minLines = 250000;
    private final List<String> lines;
    private final int start;
    private final int end;

    public WordCountRecursiveTask(List<String> lines, int start, int end) {
        this.lines = lines;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Map<String, Integer> compute() {
        int size = end - start;
        if (size <= minLines) {
            return processLines(lines, start, end);
        }
        int mid = start + size / 2;
        WordCountRecursiveTask leftTask = new WordCountRecursiveTask(lines, start, mid);
        WordCountRecursiveTask rightTask = new WordCountRecursiveTask(lines, mid, end);
        leftTask.fork();
        Map<String, Integer> rightResult = rightTask.compute();
        Map<String, Integer> leftResult = leftTask.join();
        leftResult.forEach((key, value) ->
                rightResult.merge(key, value, Integer::sum)
        );
        return rightResult;
    }
}
