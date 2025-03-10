package com.cognitree.internship.exp_eval;

import java.util.*;

public class ExpressionCalculator {

    public double calculate(HashMap<String, Double> userValues, List<String> parsedExpression) {
        double result = calculatePostfix(userValues, parsedExpression);
        return result;
    }

    private double calculatePostfix(HashMap<String, Double> variables, List<String> parsedExpression) {
        Stack<Double> stack = new Stack<>();
        for (String token : parsedExpression) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    System.out.print("Insufficient operands for operator " + token);
                    return 0;
                }
                double value2 = stack.pop();
                double value1 = stack.pop();
                double result = arithmaticOperation(value1, value2, token);
                stack.push(result);
            } else {
                stack.push(variables.get(token));
            }
        }
        return stack.pop();
    }

    private double arithmaticOperation(double value1, double value2, String token) {
        switch (token) {
            case "+":
                return value1 + value2;
            case "-":
                return value1 - value2;
            case "*":
                return value1 * value2;
            case "/":
                return value1 / value2;
            case "^":
                return Math.pow(value1, value2);
            default:
                throw new RuntimeException("Unknown operator: " + token);
        }
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        char operator = token.charAt(0);
        return operator == '+' || operator == '-' || operator == '*'
                || operator == '/' || operator == '^';
    }
}