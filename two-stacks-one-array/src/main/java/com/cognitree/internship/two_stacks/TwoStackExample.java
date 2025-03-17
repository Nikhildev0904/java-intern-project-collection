package com.cognitree.internship.two_stacks;

public class TwoStackExample {
    public static void main(String[] args) {
        TwoStack<Integer> twoStack = new TwoStack<>(5);
        twoStack.pushLeft(5);
        twoStack.pushRight(6);
        twoStack.pushLeft(7);
        twoStack.pushRight(8);
        twoStack.pushLeft(10);
        //twoStack.pushToStackTwo(6);
        System.out.println(twoStack.leftSize());
        System.out.println(twoStack.rightSize());
        while (!twoStack.isLeftEmpty()) {
            System.out.print(twoStack.peekLeft() + " ");
            twoStack.popLeft();
        }
        System.out.println();
        while (!twoStack.isRightEmpty()) {
            System.out.print(twoStack.peekRight() + " ");
            twoStack.popRight();
        }
    }
}
