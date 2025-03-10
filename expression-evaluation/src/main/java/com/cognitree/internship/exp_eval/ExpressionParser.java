package com.cognitree.internship.exp_eval;

import java.util.*;

public class ExpressionParser {

    public List<String> parsingExpression(String expression){
        List<String> tokenisedExpression = tokenise(expression);
        List<String> parsedExpression = null;
        if (tokenisedExpression != null) {
            parsedExpression = infixToPostfix(tokenisedExpression);
        }
        return parsedExpression;
    }

    public Set<String> extractVariables(List<String> parsedExpression) {
        Set<String> variables = new HashSet<>();
        for (String token : parsedExpression) {
            if (!isNumber(token) && !isOperator(token) &&
                    !token.equals("(") && !token.equals(")")) {
                variables.add(token);
            }
        }
        return variables;
    }

    public double evaluateExpression(HashMap<String, Double> variables, List<String> parsedExpression) {
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
                double result = evaluate(value1, value2, token);
                stack.push(result);
            } else {
                stack.push(variables.get(token));
            }
        }
        return stack.pop();
    }

    private List<String> tokenise(String input) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        int sizeOfInput = input.length();
        while (i < sizeOfInput) {
            char character = input.charAt(i);
            if (character == ' ') {
                i++;
            } else if (Character.isDigit(character) || character == '.') {
                int start = i;
                while (i < sizeOfInput && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    i++;
                }
                tokens.add(input.substring(start, i));
            } else if (Character.isLetter(character)) {
                int start = i;
                while (i < sizeOfInput && Character.isLetter(input.charAt(i))) {
                    i++;
                }
                tokens.add(input.substring(start, i));
            } else if (isOperator(String.valueOf(character)) || character == '(' || character == ')') {
                tokens.add(String.valueOf(character));
                i++;
            } else {
                return null;
            }
        }
        return tokens;
    }

    private List<String> infixToPostfix(List<String> expression) {
        List<String> postfixExpression = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : expression) {
            if (isNumber(token) || Character.isLetter(token.charAt(0))) {
                postfixExpression.add(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) &&
                        precedence(stack.peek()) >= precedence(token)) {
                    postfixExpression.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfixExpression.add(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            String top = stack.pop();
            postfixExpression.add(top);
        }
        return postfixExpression;
    }

    private int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }

    private double evaluate(double value1, double value2, String token) {
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

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        char operator = token.charAt(0);
        return operator == '+' || operator == '-' || operator == '*'
                || operator == '/' || operator == '^';
    }
}
