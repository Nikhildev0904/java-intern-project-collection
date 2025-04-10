package com.cognitree.internship.infinite_stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InfiniteStreamAverage: A class to find the average of a infinite Stream of Numbers
 */
public class InfiniteStreamAverage<T extends Number> {

    private static final Logger logger = LoggerFactory.getLogger(InfiniteStreamAverage.class);

    private final CircularQueue<T> window;

    private double sum;

    /**
     * Constructor to initialize object
     *
     * @param size - size of the window
     */
    public InfiniteStreamAverage(int size) {
        logger.info("Initialising window with size: {}", size);
        this.window = new CircularQueue<>(size);
        this.sum = 0;
    }

    /**
     * Function to calculate running average of the window
     *
     * @param element - Number to be inserted
     * @return - Returns Running average of the window
     */
    public double computeRunningAverage(T element) {
        T temp = window.enqueue(element);
        if (temp != null)
            sum -= temp.doubleValue();
        sum += element.doubleValue();
        return sum / window.getLength();
    }

}