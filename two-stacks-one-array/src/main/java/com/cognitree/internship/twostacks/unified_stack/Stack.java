package com.cognitree.internship.twostacks.unified_stack;

import java.util.Iterator;

public interface Stack<T> extends Iterable<T> {
    void push(T element);

    T pop();

    T peek();

    int size();

    boolean isEmpty();

    boolean isFull();

    @Override
    Iterator<T> iterator();
}
