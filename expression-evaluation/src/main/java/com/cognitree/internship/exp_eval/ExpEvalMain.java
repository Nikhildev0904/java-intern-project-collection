package com.cognitree.internship.exp_eval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ExpEvalMain {
    private static final Logger logger = LoggerFactory.getLogger(ExpEvalMain.class);

    public static void main(String[] args) {
        logger.info("Application started");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine().trim();
        ExpEval expressionEvaluator = null;
        try {
            expressionEvaluator = new ExpEval(expression);
        } catch (RuntimeException e) {
            logger.info("Error parsing the expression :{}", e.getMessage());
            return;
        }
        Set<String> variables = expressionEvaluator.getVariables();
        while (true) {
            try {
                Map<String, Double> variableValues = promptVariableValues(variables, scanner);
                double result = expressionEvaluator.calculate(variableValues);
                System.out.println("Result: " + result);
            } catch (RuntimeException e) {
                logger.info("Error during evaluation: {}", e.getMessage());
            }
            System.out.print("Try again with new input values? (y/n): ");
            String userPrompt = scanner.nextLine();
            if (userPrompt.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private static Map<String, Double> promptVariableValues(Set<String> extractedVariables, Scanner scanner) {
        HashMap<String, Double> values = new HashMap<>();
        for (String variable : extractedVariables) {
            System.out.print("Enter the value of " + variable + ": ");
            double value;
            try {
                value = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                logger.error("Invalid number");
                throw new RuntimeException("Invalid Number " + e);
            }
            values.put(variable, value);
        }
        return values;
    }
}
