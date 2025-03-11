package com.cognitree.internship.exp_eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;


public class ExpressionParser {
    public List<String> parsingExpression(String expression) throws RuntimeException {
        List<String> parsedExpression;
        try {
            List<String> tokenisedExpression = tokenise(expression);
            parsedExpression = infixToPostfix(tokenisedExpression);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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


    private List<String> tokenise(String rawExpression) throws RuntimeException {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        int sizeOfInput = rawExpression.length();
        while (i < sizeOfInput) {
            char character = rawExpression.charAt(i);
            if (character == ' ') {
                i++;
            } else if (Character.isDigit(character) || character == '.') {
                int start = i;
                while (i < sizeOfInput && (Character.isDigit(rawExpression.charAt(i)) || rawExpression.charAt(i) == '.')) {
                    i++;
                }
                tokens.add(rawExpression.substring(start, i));
            } else if (Character.isLetter(character)) {
                int start = i;
                while (i < sizeOfInput && Character.isLetter(rawExpression.charAt(i))) {
                    i++;
                }
                tokens.add(rawExpression.substring(start, i));
            } else if (isOperator(String.valueOf(character)) || character == '(' || character == ')') {
                tokens.add(String.valueOf(character));
                i++;
            } else {
                System.out.println("Wrong Input");
                throw new RuntimeException("Please provide valid Input");
            }
        }
        return tokens;
    }

    private List<String> infixToPostfix(List<String> tokenizedExpression) {
        List<String> postfixExpression = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : tokenizedExpression) {
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
