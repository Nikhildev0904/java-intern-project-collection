package com.cognitree.internship.infinite_stream;

import java.util.Scanner;

public class InfiniteStreamImplementation {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        System.out.print("Enter the size of the window: ");
        int size = sc.nextInt();
        sc.nextLine();


        System.out.println("Enter the numbers: ");
        String[] str = sc.nextLine().split(" ");

        InfiniteStreamAverage<Double> obj = new InfiniteStreamAverage<>(size);

        for(String s:str){
            System.out.println(obj.computeRunningAverage(Double.parseDouble(s)));
        }
    }

}
