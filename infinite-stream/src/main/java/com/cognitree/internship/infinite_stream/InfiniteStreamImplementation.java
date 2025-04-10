package com.cognitree.internship.infinite_stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class InfiniteStreamImplementation {

    private static final Logger logger = LoggerFactory.getLogger(InfiniteStreamImplementation.class);

    public static void main(String[] args) {
        logger.info("Infinite Stream Application started");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();
        try {
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
        } catch (NumberFormatException e) {
            logger.error("Invalid input type: ", e);
        }
    }
}
