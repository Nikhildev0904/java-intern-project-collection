package com.cognitree.internship.twostacks.approach1;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.pushLeft(5);
        twoStacks.pushRight(6);
        twoStacks.pushLeft(7);
        twoStacks.pushRight(8);
        twoStacks.pushLeft(10);
        System.out.println(twoStacks.leftSize());
        System.out.println(twoStacks.rightSize());
        while (!twoStacks.isLeftEmpty()) {
            System.out.print(twoStacks.peekLeft() + " ");
            twoStacks.popLeft();
        }
        System.out.println();
        while (!twoStacks.isRightEmpty()) {
            System.out.print(twoStacks.peekRight() + " ");
            twoStacks.popRight();
        }
    }
}
