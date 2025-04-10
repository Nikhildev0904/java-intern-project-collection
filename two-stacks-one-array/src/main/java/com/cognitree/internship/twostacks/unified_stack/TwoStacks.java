package com.cognitree.internship.twostacks.unified_stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoStacks<T> {

    private static final Logger logger = LoggerFactory.getLogger(TwoStacks.class);

    private final T[] array;
    private final LeftStack leftStack;
    private final RightStack rightStack;

    public TwoStacks(int size) {
        array = (T[]) new Object[size];
        leftStack = new LeftStack();
        rightStack = new RightStack();
        logger.info("Initialized twostacks with size: {}", size);
    }

    public Stack<T> getLeftStack() {
        return leftStack;
    }

    public Iterator<T> getLeftIterator() {
        return leftStack.iterator();
    }

    public Stack<T> getRightStack() {
        return rightStack;
    }

    public Iterator<T> getRightIterator() {
        return rightStack.iterator();
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
                        throw new NoSuchElementException("next() called but no more elements available in the left stack");
                    }
                    return array[current--];
                }
            };
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
                        throw new NoSuchElementException("next() called but no more elements available in the right stack");
                    }
                    return array[current++];
                }
            };
        }
    }
}
