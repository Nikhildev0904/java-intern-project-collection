package com.cognitree.internship.exp_eval;

import java.util.Scanner;

public class ExpressionEvaluatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
        boolean processed = expressionEvaluator.processingInput(input);
        if (!processed) {
            System.out.println("Invalid Input");
            return;
        }
        while (true) {
            double result;
            try {
                result = expressionEvaluator.evaluate();
            } catch (RuntimeException e) {
                System.out.println("Invalid Number :" + e);
                return;
            }
            System.out.println("Result: " + result);
            System.out.print("Try again with new input values? (y/n): ");
            String userPrompt = scanner.nextLine();
            if (userPrompt.equalsIgnoreCase("n")) {
                break;
            }
        }
    }
}
