package com.cognitree.internship.twostacks.individualops;

import java.util.Iterator;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.pushLeft(5);
        twoStacks.pushRight(6);
        twoStacks.pushLeft(7);
        twoStacks.pushRight(8);
        twoStacks.pushLeft(10);
        System.out.println(twoStacks.sizeLeft());
        System.out.println(twoStacks.sizeRight());
        Iterator<Integer> leftIterator = twoStacks.getIteratorLeft();
        while (leftIterator.hasNext()) {
            System.out.print(leftIterator.next() + " ");
        }
        System.out.println();
        Iterator<Integer> rightIterator = twoStacks.getIteratorRight();
        while (rightIterator.hasNext()) {
            System.out.print(rightIterator.next() + " ");
        }
    }
}
