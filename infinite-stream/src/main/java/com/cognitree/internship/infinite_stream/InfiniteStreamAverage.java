package com.cognitree.internship.infinite_stream;


/**
 *  InfiniteStreamAverage: A class to find the average of a infinite Stream of Numbers
 */
public class InfiniteStreamAverage<T extends Number>{
    private final int size;
    private final CircularQueue<T> window;
    private double sum;

    /**
     * Constuctor to initialize object
     * @param size - size of the window
     */
    public InfiniteStreamAverage(int size) {
        this.size = size;
        this.window = new CircularQueue<>(size);
        this.sum = 0;
    }


    /**
     * Function to calculate running average of the window
     * @param element - Number to be inserted
     * @return - Returns Running average of the window
     */
    double computeRunningAverage(T element){
        if (window.getLength() == size) {
            sum -= window.dequeue().doubleValue();
        }


        window.enqueue(element);
        sum += element.doubleValue();

        return sum / window.getLength();
    }

}