package com.cognitree.internship.twostacks.approach2;

import java.util.NoSuchElementException;

public class TwoStacks<T> {
    private final LeftStack leftStack;
    private final RightStack rightStack;
    private final T[] array;

    public TwoStacks(int size) {
        array = (T[]) new Object[size];
        leftStack = new LeftStack();
        rightStack = new RightStack();
    }

    public void push(T element, String type) {
        if (type.equals("left")) {
            leftStack.push(element);
        } else {
            rightStack.push(element);
        }
    }

    public T pop(String type) {
        if (type.equals("left")) {
            return leftStack.pop();
        } else {
            return rightStack.pop();
        }
    }

    public T peek(String type) {
        if (type.equals("left")) {
            return leftStack.peek();
        } else {
            return rightStack.peek();
        }
    }

    public int size(String type) {
        if (type.equals("left")) {
            return leftStack.leftTop + 1;
        } else {
            return array.length - rightStack.rightTop;
        }
    }

    public boolean isEmpty(String type) {
        if (type.equals("left")) {
            return leftStack.isEmpty();
        } else {
            return rightStack.isEmpty();
        }
    }

    public boolean isFull(String type) {
        if (type.equals("left")) {
            return leftStack.isFull();
        } else {
            return rightStack.isFull();
        }
    }

    private class LeftStack {
        private int leftTop = -1;

        private void push(T element) {
            if (isFull()) {
                throw new IllegalStateException("Left stack overflow");
            }
            leftTop++;
            array[leftTop] = element;
        }

        private T pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Left stack underflow");
            }
            T last = array[leftTop];
            array[leftTop] = null;
            leftTop--;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Left stack is empty");
            }
            return array[leftTop];
        }

        private boolean isFull() {
            return leftTop + 1 == rightStack.rightTop;
        }

        private boolean isEmpty() {
            return leftTop == -1;
        }
    }

    private class RightStack {
        private int rightTop = array.length;

        private void push(T element) {
            if (isFull()) {
                throw new IllegalStateException("Right stack overflow");
            }
            rightTop--;
            array[rightTop] = element;
        }

        private T pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Right stack underflow");
            }
            T last = array[rightTop];
            array[rightTop] = null;
            rightTop++;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Right stack is empty");
            }
            return array[rightTop];
        }

        private boolean isFull() {
            return leftStack.leftTop + 1 == rightTop;
        }

        private boolean isEmpty() {
            return rightTop == array.length;
        }
    }
}
