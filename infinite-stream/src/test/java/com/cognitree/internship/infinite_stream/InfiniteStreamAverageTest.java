package com.cognitree.internship.infinite_stream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfiniteStreamAverageTest {

    @Test
    void computeRunningAverageBeforeWindowSlides() {
        InfiniteStreamAverage<Integer> infiniteStreamAverage = new InfiniteStreamAverage<>(3);
        assertEquals(1, infiniteStreamAverage.computeRunningAverage(1));
        assertEquals(1.5, infiniteStreamAverage.computeRunningAverage(2));
        assertEquals(2, infiniteStreamAverage.computeRunningAverage(3));
        //window is full
    }

    @Test
    void computeRunningAverageAfterWindowSlides() {
        InfiniteStreamAverage<Integer> infiniteStreamAverage = new InfiniteStreamAverage<>(3);
        infiniteStreamAverage.computeRunningAverage(1);
        infiniteStreamAverage.computeRunningAverage(2);
        infiniteStreamAverage.computeRunningAverage(3);
        //window is full, adding more elements will slide the window
        assertEquals(3, infiniteStreamAverage.computeRunningAverage(4));
        assertEquals(4, infiniteStreamAverage.computeRunningAverage(5));
    }
}