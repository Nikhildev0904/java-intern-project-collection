package com.cognitree.internship.infinite_stream;

import java.util.Scanner;

public class InfiniteStreamImplementation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();
        InfiniteStreamAverage<Double> object = new InfiniteStreamAverage<>(size);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
                break;
            String[] numbers = line.split(" ");
            for (String number : numbers) {
                System.out.println(object.computeRunningAverage(Double.parseDouble(number)));
            }
        }
    }

}
