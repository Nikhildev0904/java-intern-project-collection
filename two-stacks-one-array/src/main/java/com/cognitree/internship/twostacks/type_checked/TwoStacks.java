package com.cognitree.internship.twostacks.type_checked;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class TwoStacks<T> {
    private static final Logger logger = LoggerFactory.getLogger(TwoStacks.class);
    private final LeftStack leftStack;
    private final RightStack rightStack;
    private final T[] array;

    public enum StackType {LEFT, RIGHT}

    public TwoStacks(int size) {
        array = (T[]) new Object[size];
        leftStack = new LeftStack();
        rightStack = new RightStack();
        logger.info("Initialized twostacks with size: {}",size);
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
                logger.error("Left stack overflow");
                throw new IllegalStateException("Left stack overflow");
            }
            leftTop++;
            array[leftTop] = element;
        }

        private T pop() {
            if (isEmpty()) {
                logger.error("Left stack underflow");
                throw new NoSuchElementException("Left stack underflow");
            }
            T last = array[leftTop];
            array[leftTop] = null;
            leftTop--;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                logger.error("Left stack is empty");
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
                        logger.error("next() called but no more elements available in the left stack");
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
                logger.error("Right stack overflow");
                throw new IllegalStateException("Right stack overflow");
            }
            rightTop--;
            array[rightTop] = element;
        }

        private T pop() {
            if (isEmpty()) {
                logger.error("Right stack underflow");
                throw new NoSuchElementException("Right stack underflow");
            }
            T last = array[rightTop];
            array[rightTop] = null;
            rightTop++;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                logger.error("Right stack is empty");
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
                        logger.error("next() called but no more elements available in the right stack");
                        throw new NoSuchElementException("Right stack is empty");
                    }
                    return array[current++];
                }
            };
        }
    }
}
