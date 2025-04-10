package com.cognitree.internship.twostacks.unified_stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoStackExample {

    private static final Logger logger = LoggerFactory.getLogger(TwoStackExample.class);

    public static void main(String[] args) {
        logger.info("TwoStackExample Application started");
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        Stack<Integer> leftStack = twoStacks.getLeftStack();
        Stack<Integer> rightStack = twoStacks.getRightStack();
        leftStack.push(5);
        rightStack.push(6);
        leftStack.push(7);
        rightStack.push(8);
        leftStack.push(10);
        logger.debug("Iterating left stack (size={})", leftStack.size());
        for (Integer i : leftStack) {
            System.out.print(i + " ");
        }
        System.out.println();
        logger.debug("Iterating right stack (size={})", rightStack.size());
        for (Integer i : rightStack) {
            System.out.print(i + " ");
        }
    }
}
