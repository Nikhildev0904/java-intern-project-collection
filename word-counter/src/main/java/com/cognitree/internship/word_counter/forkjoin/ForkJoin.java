package com.cognitree.internship.word_counter.forkjoin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ForkJoin {

    public Map<String, Integer> getWordCountWithForkJoinTask(List<String> lines) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        WordCountRecursiveTask task = new WordCountRecursiveTask(lines, 0, lines.size());
        return forkJoinPool.invoke(task);
    }

    public Map<String, Integer> getWordCountWithForkJoinAction(List<String> lines) {
        Map<String, Integer> sharedMap = new ConcurrentHashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        WordCountRecursiveAction action = new WordCountRecursiveAction(lines, 0, lines.size(), sharedMap);
        forkJoinPool.invoke(action);
        return sharedMap;
    }
}
