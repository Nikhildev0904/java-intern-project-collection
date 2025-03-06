package com.cognitree.internship.infinite_stream;

/**
 * Class to implement immutable CircularQueue
 * @param <T> - Generic type
 */
public class CircularQueue<T extends Number> {
    private int front;
    private int rear;
    private final T[] nums;
    private int length;

    /**
     * Constructor to initialise CircularQueue
     * @param size - size of queue
     */
    CircularQueue(int size){
       this.nums = (T[]) new Number[size];
       front = -1;
       rear = -1;
       this.length = 0;
    }

    /**
     * Function to insert numbers into the queue
     * @param element - number to be inserted
     */
    public final void enqueue(T element){
       if(isFull()){
           System.out.println("Cannot Insert, queue is full");
           return;
       } else if (front == -1) {
           front = 0;
       }

       rear = ((rear + 1) % nums.length);
       nums[rear] = element;
       length++;

    }

    /**
     * Function to remove the element at the front
     * @return - The element removed
     */
    public final T dequeue(){
        if(isEmpty()){
            System.out.println("Cannot delete queue is empty");
            return null;
        }

        T temp = nums[front];

        if (front == rear) {
            front = -1;
            rear = -1;
        }
        else{
            front=((front+1) % nums.length);
        }

        length--;
        return temp;
    }

    public int getLength() {
        return length;
    }

    public boolean isFull() {
        return (rear + 1) % nums.length == front;
    }

    public boolean isEmpty() {
        return front == -1;
    }

}
