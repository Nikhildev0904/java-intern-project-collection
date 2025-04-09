package com.cognitree.internship.twostacks.unified_stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoStacks<T> {
    private static final Logger logger = LoggerFactory.getLogger(TwoStacks.class);
    private final LeftStack leftStack;
    private final RightStack rightStack;
    private final T[] array;

    public TwoStacks(int size) {
        array = (T[]) new Object[size];
        leftStack = new LeftStack();
        rightStack = new RightStack();
        logger.info("Initialized twostacks with size: {}",size);
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
                logger.error("Left stack overflow");
                throw new IllegalStateException("Left stack overflow");
            }
            leftTop++;
            array[leftTop] = element;
        }

        @Override
        public T pop() {
            if (isEmpty()) {
                logger.error("Left stack underflow");
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
                logger.error("Left stack is empty");
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
                        logger.error("next() called but no more elements available in the left stack");
                        throw new NoSuchElementException("Left stack is empty");
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
                logger.error("Right stack overflow");
                throw new IllegalStateException("Right stack overflow");
            }
            rightTop--;
            array[rightTop] = element;
        }

        @Override
        public T pop() {
            if (isEmpty()) {
                logger.error("Right stack underflow");
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
                logger.error("Right stack is empty");
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
                        logger.error("next() called but no more elements available in the right stack");
                        throw new NoSuchElementException("Right stack is empty");
                    }
                    return array[current++];
                }
            };
        }
    }
}
