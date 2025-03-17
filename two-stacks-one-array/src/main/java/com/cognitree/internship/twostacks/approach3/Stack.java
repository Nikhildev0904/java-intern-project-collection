package com.cognitree.internship.twostacks.approach3;

public interface Stack<T> {
    void push(T element);

    T pop();

    T peek();

    int size();

    boolean isEmpty();

    boolean isFull();
}
