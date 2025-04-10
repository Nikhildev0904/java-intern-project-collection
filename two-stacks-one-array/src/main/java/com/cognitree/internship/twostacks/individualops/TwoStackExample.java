package com.cognitree.internship.twostacks.individualops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class TwoStackExample {

    private static final Logger logger = LoggerFactory.getLogger(TwoStackExample.class);

    public static void main(String[] args) {
        logger.info("TwoStackExample Application started");
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.pushLeft(5);
        twoStacks.pushRight(6);
        twoStacks.pushLeft(7);
        twoStacks.pushRight(8);
        twoStacks.pushLeft(10);
        logger.debug("Iterating left stack (size={})", twoStacks.sizeLeft());
        Iterator<Integer> leftIterator = twoStacks.getIteratorLeft();
        while (leftIterator.hasNext()) {
            System.out.print(leftIterator.next() + " ");
        }
        System.out.println();
        logger.debug("Iterating right stack (size={})", twoStacks.sizeRight());
        Iterator<Integer> rightIterator = twoStacks.getIteratorRight();
        while (rightIterator.hasNext()) {
            System.out.print(rightIterator.next() + " ");
        }
    }
}
