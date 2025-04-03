package com.cognitree.internship.word_counter.forkjoin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import static com.cognitree.internship.word_counter.LineProcessor.processLines;

public class WordCountRecursiveAction extends RecursiveAction {
    private final List<String> lines;
    private final Map<String, Integer> sharedMap;
    private static final int minLines = 1000;
    private final int start;
    private final int end;

    public WordCountRecursiveAction(List<String> lines, int start, int end, Map<String, Integer> sharedMap) {
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.sharedMap = sharedMap;
    }

    @Override
    protected void compute() {
        int size = end - start;
        if (size <= minLines) {
            Map<String, Integer> localMap = processLines(lines, start, end);
            synchronized (sharedMap) {
                for (Map.Entry<String, Integer> entry : localMap.entrySet()) {
                    sharedMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
            return;
        }
        int mid = start + size / 2;
        WordCountRecursiveAction leftTask = new WordCountRecursiveAction(lines, start, mid, sharedMap);
        WordCountRecursiveAction rightTask = new WordCountRecursiveAction(lines, mid, end, sharedMap);
        invokeAll(leftTask, rightTask);
    }
}
