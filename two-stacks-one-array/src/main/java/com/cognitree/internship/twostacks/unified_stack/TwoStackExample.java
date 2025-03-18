package com.cognitree.internship.twostacks.unified_stack;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        Stack<Integer> left = twoStacks.getLeftStack();
        Stack<Integer> right = twoStacks.getRightStack();
        left.push(5);
        right.push(6);
        left.push(7);
        right.push(8);
        left.push(10);
        System.out.println(left.size());
        System.out.println(right.size());
        for (Integer i : left) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (Integer i : right) {
            System.out.print(i + " ");
        }
    }
}
