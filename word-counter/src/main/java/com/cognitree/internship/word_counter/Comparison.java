package com.cognitree.internship.word_counter;

import java.util.List;
import java.util.Map;

public class Comparison {

    public void compareAll(List<String> lines) {
        /* -- Sequential Computation -- */
        long start = System.currentTimeMillis();
        Sequential sequential = new Sequential();
        Map<String, Integer> wordCountMap = sequential.getWordCount(lines);
        long end = System.currentTimeMillis();
        System.out.println("Time taken for Sequential Computation: " + (end - start));

        /* -- Multithreading using threads - runnable with concurrent data structure */
        start = System.currentTimeMillis();
        ConcurrentRunnableThread concurrentRunnableThread = new ConcurrentRunnableThread();
        Map<String, Integer> concurrentRunnableWordCount;
        try {
            concurrentRunnableWordCount = concurrentRunnableThread.getWordCount(lines);
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
        System.out.println("Validation of results : " + compareResults(wordCountMap, concurrentRunnableWordCount));

        /* -- Multithreading using threads - runnable with synchronized block */
        start = System.currentTimeMillis();
        SyncRunnableThread syncRunnableThread = new SyncRunnableThread();
        Map<String, Integer> SyncRunnableWordCount;
        try {
            SyncRunnableWordCount = syncRunnableThread.getWordCount(lines);
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
        System.out.println("Validation of results : " + compareResults(wordCountMap, SyncRunnableWordCount));

        /* -- Multithreading using threads - callable */
        start = System.currentTimeMillis();
        CallableThread callableThread = new CallableThread();
        Map<String, Integer> callableThreadWordCount;
        try {
            callableThreadWordCount = callableThread.getWordCount(lines);
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
        System.out.println("Validation of results : " + compareResults(wordCountMap, callableThreadWordCount));

        /* -- Multithreading Using Threadpool - Runnable -- */
        start = System.currentTimeMillis();
        RunnableThreadPool runnableThreadPool = new RunnableThreadPool();
        Map<String, Integer> runnableThreadPoolWordCount;
        try {
            runnableThreadPoolWordCount = runnableThreadPool.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Threadpool using Runnable: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, runnableThreadPoolWordCount));

        /* -- Multithreading Using Threadpool - Callable -- */
        start = System.currentTimeMillis();
        CallableThreadPool callableThreadPool = new CallableThreadPool();
        Map<String, Integer> callableThreadPoolWordCount;
        try {
            callableThreadPoolWordCount = callableThreadPool.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Threadpool using Callable: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, callableThreadPoolWordCount));

        /* -- Multithreading Using Completable Future -- */
        start = System.currentTimeMillis();
        CompletableFuture completableFuture = new CompletableFuture();
        Map<String, Integer> completableFutureWordCount;
        try {
            completableFutureWordCount = completableFuture.getWordCount(lines);
        } catch (InterruptedException e) {
            System.out.println("Thread execution was interrupted: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for Completable Future: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, completableFutureWordCount));

        /* -- Multithreading Using ForkJoin - Recursive Action -- */
        start = System.currentTimeMillis();
        ForkJoin forkJoin = new ForkJoin();
        Map<String, Integer> forkJoinActionWordCount;
        try {
            forkJoinActionWordCount = forkJoin.getWordCountWithForkJoinAction(lines);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for ForkJoin RecursiveAction: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, forkJoinActionWordCount));

        /* -- Multithreading Using ForkJoin - Recursive Task -- */
        start = System.currentTimeMillis();
        Map<String, Integer> forkJoinTaskWordCount;
        try {
            forkJoinTaskWordCount = forkJoin.getWordCountWithForkJoinTask(lines);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken for ForkJoin RecursiveTask: " + (end - start));
        System.out.println("Validation of results : " + compareResults(wordCountMap, forkJoinTaskWordCount));
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

