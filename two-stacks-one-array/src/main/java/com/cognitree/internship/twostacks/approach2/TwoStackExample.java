package com.cognitree.internship.twostacks.approach2;

public class TwoStackExample {

    public static void main(String[] args) {
        TwoStacks<Integer> twoStacks = new TwoStacks<>(5);
        twoStacks.push(5, "left");
        twoStacks.push(6, "right");
        twoStacks.push(7, "left");
        twoStacks.push(8, "right");
        twoStacks.push(10, "left");
        System.out.println(twoStacks.size("left"));
        System.out.println(twoStacks.size("right"));
        while (!twoStacks.isEmpty("left")) {
            System.out.print(twoStacks.peek("left") + " ");
            twoStacks.pop("left");
        }
        System.out.println();
        while (!twoStacks.isEmpty("right")) {
            System.out.print(twoStacks.peek("right") + " ");
            twoStacks.pop("right");
        }
    }
}
