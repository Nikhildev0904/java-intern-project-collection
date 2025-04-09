package com.cognitree.internship.twostacks.type_checked;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class TwoStackExample {
    private static final Logger logger = LoggerFactory.getLogger(TwoStackExample.class);


    public static void main(String[] args) {
        logger.info("TwoStackExample Application started");
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.push(5, TwoStacks.StackType.LEFT);
        twoStacks.push(6, TwoStacks.StackType.RIGHT);
        twoStacks.push(7, TwoStacks.StackType.LEFT);
        twoStacks.push(8, TwoStacks.StackType.RIGHT);
        twoStacks.push(10, TwoStacks.StackType.LEFT);
        System.out.println(twoStacks.size(TwoStacks.StackType.LEFT));
        System.out.println(twoStacks.size(TwoStacks.StackType.RIGHT));
        logger.debug("Iterating left stack");
        Iterator<Integer> leftIterator = twoStacks.getIterator(TwoStacks.StackType.LEFT);
        while (leftIterator.hasNext()) {
            System.out.print(leftIterator.next() + " ");
        }
        System.out.println();
        logger.debug("Iterating right stack");
        Iterator<Integer> rightIterator = twoStacks.getIterator(TwoStacks.StackType.RIGHT);
        while (rightIterator.hasNext()) {
            System.out.print(rightIterator.next() + " ");
        }
    }
}
