package com.cognitree.internship.two_stacks;

public class TwoStack<T> {
    private final T[] array;
    private int elementCount = 0;

    public TwoStack(int size) {
        array = (T[]) new Object[size];
    }

    public class LeftStack {
        private int top1 = -1;

        public void push(T element) {
            if (isFull()) {
                throw new RuntimeException("Cannot Insert, array is full");
            }
            top1++;
            array[top1] = element;
            elementCount++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot delete, Stack is empty");
            }
            T last = array[top1];
            top1--;
            elementCount--;
            return last;
        }

        public T peek() {
            return array[top1];
        }

        public boolean isFull() {
            return elementCount == array.length;
        }

        public boolean isEmpty() {
            return top1 == -1;
        }
    }

    public class RightStack {
        private int top2 = array.length;

        public void push(T element) {
            if (isFull()) {
                throw new RuntimeException("Cannot Insert, array is full");
            }
            top2--;
            array[top2] = element;
            elementCount++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot delete, Stack is empty");
            }
            T last = array[top2];
            top2++;
            elementCount--;
            return last;
        }

        public T peek() {
            return array[top2];
        }

        public boolean isFull() {
            return elementCount == array.length;
        }

        public boolean isEmpty() {
            return top2 == array.length;
        }
    }
}
