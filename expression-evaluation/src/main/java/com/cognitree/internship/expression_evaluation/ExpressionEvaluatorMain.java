package com.cognitree.internship.expression_evaluation;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ExpressionEvaluatorMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
        List<String> expression = expressionEvaluator.tokenise(input);
        List<String> parsedExpression = null;
        if (expression != null)
            parsedExpression = expressionEvaluator.infixToPostfix(expression);
        else
            return;

        Set<String> variables = expressionEvaluator.extractVariables(expression);
        while (true) {
            HashMap<String, Double> valuesOfVariables = new HashMap<>();
            for (String variable : variables) {
                System.out.print("Enter the value of " + variable + ": ");
                double value = Double.parseDouble(scanner.nextLine());
                valuesOfVariables.put(variable, value);
            }
            double result = expressionEvaluator.evaluateExpression(parsedExpression, valuesOfVariables);
            System.out.println("Result: " + result);
            if(!variables.isEmpty()) {
                System.out.print("Try again with new input values? (y/n): ");
                String userPrompt = scanner.nextLine();
                if (userPrompt.equalsIgnoreCase("n")) {
                    break;
                }
            }
            else{
                break;
            }
        }

    }
}
