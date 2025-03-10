package com.cognitree.internship.exp_eval;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ExpressionEvaluatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine().trim();
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
        boolean isParsed = expressionEvaluator.tokenizeAndConvertToPostfix(expression);
        if (!isParsed) {
            System.out.println("Invalid Input");
            return;
        }
        Set<String> extractedVariables = expressionEvaluator.extractVariables();
        while (true) {
            HashMap<String, Double> valuesOfVariables;
            try {
                valuesOfVariables = variableValues(extractedVariables, scanner);
            } catch (RuntimeException e) {
                System.out.println("Invalid Number :" + e);
                return;
            }
            double result = expressionEvaluator.evaluate(valuesOfVariables);
            System.out.println("Result: " + result);
            System.out.print("Try again with new input values? (y/n): ");
            String userPrompt = scanner.nextLine();
            if (userPrompt.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private static HashMap<String, Double> variableValues(Set<String> extractedVariables,Scanner scanner){
        HashMap<String, Double> values = new HashMap<>();
        for (String variable : extractedVariables) {
            System.out.print("Enter the value of " + variable + ": ");
            double value;
            try {
                value = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
            values.put(variable, value);
        }
        return values;
    }
}
