package com.cognitree.internship.expression_evaluation;

import java.util.Scanner;

public class ExpressionEvaluatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
        expressionEvaluator.processingInput(input);
        while (true) {
            double result = expressionEvaluator.evaluate();
            System.out.println("Result: " + result);
            System.out.print("Try again with new input values? (y/n): ");
            String userPrompt = scanner.nextLine();
            if (userPrompt.equalsIgnoreCase("n")) {
                break;
            }
        }
    }
}
