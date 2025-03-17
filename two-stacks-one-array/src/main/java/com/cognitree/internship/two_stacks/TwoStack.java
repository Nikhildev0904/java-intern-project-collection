package com.cognitree.internship.two_stacks;

public class TwoStack<T> {
    private final LeftStack leftStack;
    private final RightStack rightStack;
    private final T[] array;

    public TwoStack(int size) {
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

    public int leftSize() {
        return leftStack.top1 + 1;
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

    public int rightSize() {
        return array.length - rightStack.top2;
    }

    public boolean isRightEmpty() {
        return rightStack.isEmpty();
    }

    public boolean isRightFull() {
        return rightStack.isFull();
    }

    private class LeftStack {
        private int top1 = -1;

        private void push(T element) {
            if (isFull()) {
                throw new RuntimeException("Cannot Insert, array is full");
            }
            top1++;
            array[top1] = element;
        }

        private T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot pop, Stack is empty");
            }
            T last = array[top1];
            array[top1] = null;
            top1--;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot peek, Stack is empty");
            }
            return array[top1];
        }

        private boolean isFull() {
            return top1 + 1 == rightStack.top2;
        }

        private boolean isEmpty() {
            return top1 == -1;
        }
    }

    private class RightStack {
        private int top2 = array.length;

        private void push(T element) {
            if (isFull()) {
                throw new RuntimeException("Cannot Insert, array is full");
            }
            top2--;
            array[top2] = element;
        }

        private T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot delete, Stack is empty");
            }
            T last = array[top2];
            array[top2] = null;
            top2++;
            return last;
        }

        private T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Cannot peek, Stack is empty");
            }
            return array[top2];
        }

        private boolean isFull() {
            return leftStack.top1 + 1 == top2;
        }

        private boolean isEmpty() {
            return top2 == array.length;
        }
    }
}
