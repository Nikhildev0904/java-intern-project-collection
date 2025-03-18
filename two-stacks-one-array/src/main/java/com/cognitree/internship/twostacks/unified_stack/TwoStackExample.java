package com.cognitree.internship.twostacks.unified_stack;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        Stack<Integer> leftStack = twoStacks.getLeftStack();
        Stack<Integer> rightStack = twoStacks.getRightStack();
        leftStack.push(5);
        rightStack.push(6);
        leftStack.push(7);
        rightStack.push(8);
        leftStack.push(10);
        System.out.println(leftStack.size());
        System.out.println(rightStack.size());
        for (Integer i : leftStack) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (Integer i : rightStack) {
            System.out.print(i + " ");
        }
    }
}
