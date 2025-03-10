package com.cognitree.internship.exp_eval;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ExpressionEvaluatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine().trim();
        ExpressionEvaluation expressionEvaluator = new ExpressionEvaluation();
        Set<String> variables = expressionEvaluator.extractVariables(expression);
        while (true) {
            HashMap<String, Double> variableValues;
            try {
                variableValues = setVariableValues(variables, scanner);
            } catch (RuntimeException e) {
                System.out.println("Invalid Number :" + e);
                return;
            }
            double result = expressionEvaluator.evaluate(variableValues);
            System.out.println("Result: " + result);
            System.out.print("Try again with new input values? (y/n): ");
            String userPrompt = scanner.nextLine();
            if (userPrompt.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private static HashMap<String, Double> setVariableValues(Set<String> extractedVariables, Scanner scanner) {
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
