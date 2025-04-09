package com.cognitree.internship.twostacks.individualops;

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

    public void pushLeft(T element) {
        leftStack.push(element);
    }

    public T popLeft() {
        return leftStack.pop();
    }

    public T peekLeft() {
        return leftStack.peek();
    }

    public int sizeLeft() {
        return leftStack.leftTop + 1;
    }

    public Iterator<T> getIteratorLeft() {
        return leftStack.iterator();
    }

    public boolean isEmptyLeft() {
        return leftStack.isEmpty();
    }

    public boolean isFullLeft() {
        return leftStack.isFull();
    }

    public void pushRight(T element) {
        rightStack.push(element);
    }

    public T popRight() {
        return rightStack.pop();
    }

    public T peekRight() {
        return rightStack.peek();
    }

    public int sizeRight() {
        return array.length - rightStack.rightTop;
    }

    public Iterator<T> getIteratorRight() {
        return rightStack.iterator();
    }

    public boolean isEmptyRight() {
        return rightStack.isEmpty();
    }

    public boolean isFullRight() {
        return rightStack.isFull();
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
