package com.cognitree.internship.twostacks.individualops;

import java.util.Iterator;
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

    public Iterator<T> getLeftIterator() {
        return leftStack.iterator();
    }

    public boolean isLeftEmpty() {
        return leftStack.isEmpty();
    }

    public boolean isLeftFull() {
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

    public Iterator<T> getRightIterator() {
        return rightStack.iterator();
    }

    public boolean isRightEmpty() {
        return rightStack.isEmpty();
    }

    public boolean isRightFull() {
        return rightStack.isFull();
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
