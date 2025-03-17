package com.cognitree.internship.twostacks.approach3;

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
        while (!left.isEmpty()) {
            System.out.print(left.peek() + " ");
            left.pop();
        }
        System.out.println();
        while (!right.isEmpty()) {
            System.out.print(right.peek() + " ");
            right.pop();
        }
    }
}
