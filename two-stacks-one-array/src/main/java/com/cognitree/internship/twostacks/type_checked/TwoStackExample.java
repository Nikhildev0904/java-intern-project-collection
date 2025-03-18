package com.cognitree.internship.twostacks.type_checked;

import java.util.Iterator;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.push(5, TwoStacks.StackType.LEFT);
        twoStacks.push(6, TwoStacks.StackType.RIGHT);
        twoStacks.push(7, TwoStacks.StackType.LEFT);
        twoStacks.push(8, TwoStacks.StackType.RIGHT);
        twoStacks.push(10, TwoStacks.StackType.LEFT);
        System.out.println(twoStacks.size(TwoStacks.StackType.LEFT));
        System.out.println(twoStacks.size(TwoStacks.StackType.RIGHT));
        Iterator<Integer> leftIterator = twoStacks.getLeftIterator();
        while (leftIterator.hasNext()) {
            System.out.print(leftIterator.next() + " ");
        }
        System.out.println();
        Iterator<Integer> rightIterator = twoStacks.getRightIterator();
        while (rightIterator.hasNext()) {
            System.out.print(rightIterator.next() + " ");
        }
    }
}
