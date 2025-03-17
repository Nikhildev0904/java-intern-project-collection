package com.cognitree.internship.twostacks.approach3;

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

    public Stack<T> getLeftStack() {
        return leftStack;
    }

    public Stack<T> getRightStack() {
        return rightStack;
    }

    private class LeftStack implements Stack<T> {
        private int leftTop = -1;

        @Override
        public void push(T element) {
            if (isFull()) {
                throw new IllegalStateException("Left stack overflow");
            }
            leftTop++;
            array[leftTop] = element;
        }

        @Override
        public T pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Left stack underflow");
            }
            T last = array[leftTop];
            array[leftTop] = null;
            leftTop--;
            return last;
        }

        @Override
        public T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Left stack is empty");
            }
            return array[leftTop];
        }

        @Override
        public int size() {
            return leftTop + 1;
        }

        @Override
        public boolean isFull() {
            return leftTop + 1 == rightStack.rightTop;
        }

        @Override
        public boolean isEmpty() {
            return leftTop == -1;
        }
    }

    private class RightStack implements Stack<T> {
        private int rightTop = array.length;

        @Override
        public void push(T element) {
            if (isFull()) {
                throw new IllegalStateException("Right stack overflow");
            }
            rightTop--;
            array[rightTop] = element;
        }

        @Override
        public T pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Right stack underflow");
            }
            T last = array[rightTop];
            array[rightTop] = null;
            rightTop++;
            return last;
        }

        @Override
        public T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Right stack is empty");
            }
            return array[rightTop];
        }

        @Override
        public int size() {
            return array.length - rightTop;
        }

        @Override
        public boolean isFull() {
            return leftStack.leftTop + 1 == rightTop;
        }

        @Override
        public boolean isEmpty() {
            return rightTop == array.length;
        }
    }
}
