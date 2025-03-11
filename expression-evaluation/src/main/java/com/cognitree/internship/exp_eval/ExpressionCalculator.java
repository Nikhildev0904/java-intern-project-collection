package com.cognitree.internship.exp_eval;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionCalculator {

    public double calculate(Map<String, Double> variables, List<String> parsedExpression) {
        double result = calculatePostfix(variables, parsedExpression);
        return result;
    }

    private double calculatePostfix(Map<String, Double> variables, List<String> parsedExpression) {
        Stack<Double> stack = new Stack<>();
        for (String token : parsedExpression) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new RuntimeException("Insufficient Operands");
                }
                double value2 = stack.pop();
                double value1 = stack.pop();
                double result = arithmeticOperation(value1, value2, token);
                stack.push(result);
            } else {
                stack.push(variables.get(token));
            }
        }
        return stack.pop();
    }

    private double arithmeticOperation(double value1, double value2, String token) {
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