package com.cognitree.internship.two_stacks;

public class TwoStackExample {
    public static void main(String[] args) {
        TwoStack<Integer> twoStack = new TwoStack<>(5);
        TwoStack<Integer>.LeftStack stackOne = twoStack.new LeftStack();
        stackOne.push(5);
        TwoStack<Integer>.RightStack stackTwo = twoStack.new RightStack();
        System.out.println(stackTwo.isEmpty());
        //stackTwo.pop();
        stackTwo.push(10);
        stackTwo.push(12);
        stackTwo.push(6);
        stackTwo.push(9);
        //stackTwo.push(4);
        while (!stackOne.isEmpty()) {
            System.out.print(stackOne.peek() + " ");
            stackOne.pop();
        }
        System.out.println();
        while (!stackTwo.isEmpty()) {
            System.out.print(stackTwo.peek() + " ");
            stackTwo.pop();
        }

    }
}
