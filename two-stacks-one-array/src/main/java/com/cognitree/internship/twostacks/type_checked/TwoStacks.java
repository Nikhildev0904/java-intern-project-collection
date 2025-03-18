package com.cognitree.internship.twostacks.type_checked;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class TwoStacks<T> {
    private final LeftStack leftStack;
    private final RightStack rightStack;
    private final T[] array;

    public enum StackType {LEFT, RIGHT}

    public TwoStacks(int size) {
        array = (T[]) new Object[size];
        leftStack = new LeftStack();
        rightStack = new RightStack();
    }

    public void push(T element, StackType type) {
        if (type == StackType.LEFT) {
            leftStack.push(element);
        } else {
            rightStack.push(element);
        }
    }

    public T pop(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.pop();
        } else {
            return rightStack.pop();
        }
    }

    public T peek(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.peek();
        } else {
            return rightStack.peek();
        }
    }

    public int size(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.leftTop + 1;
        } else {
            return array.length - rightStack.rightTop;
        }
    }

    public Iterator<T> getIterator(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.iterator();
        } else {
            return rightStack.iterator();
        }
    }

    public boolean isEmpty(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.isEmpty();
        } else {
            return rightStack.isEmpty();
        }
    }

    public boolean isFull(StackType type) {
        if (type == StackType.LEFT) {
            return leftStack.isFull();
        } else {
            return rightStack.isFull();
        }
    }


    private class LeftStack implements Iterable<T> {
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

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int current = leftTop;

                @Override
                public boolean hasNext() {
                    return current >= 0;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("Left stack is empty");
                    }
                    return array[current--];
                }
            };
        }
    }

    private class RightStack implements Iterable<T> {
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

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int current = rightTop;

                @Override
                public boolean hasNext() {
                    return current < array.length;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("Right stack is empty");
                    }
                    return array[current++];
                }
            };
        }
    }
}
