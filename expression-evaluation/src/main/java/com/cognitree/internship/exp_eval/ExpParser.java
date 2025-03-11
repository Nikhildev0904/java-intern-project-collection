package com.cognitree.internship.exp_eval;

import com.cognitree.internship.exp_eval.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

public class ExpParser {
    private final List<Token> parsedExpression;

    public ExpParser(String expression) {
        this.parsedExpression = parseExpression(expression);
    }

    public Set<String> extractVariables() {
        Set<String> variables = new HashSet<>();
        for (Token token : parsedExpression) {
            if (token instanceof VariableToken) {
                variables.add(((VariableToken) token).getVarName());
            }
        }
        return variables;
    }

    public List<Token> getParsedExpression() {
        return parsedExpression;
    }

    private List<Token> parseExpression(String expression) throws RuntimeException {
        List<Token> parsedExpression;
        try {
            List<Token> tokenisedExpression = tokeniseExpression(expression);
            parsedExpression = convertInfixToPostfix(tokenisedExpression);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return parsedExpression;
    }

    private List<Token> tokeniseExpression(String rawExpression) throws RuntimeException {
        List<Token> tokens = new ArrayList<>();
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
                double value = Double.parseDouble(rawExpression.substring(start, i));
                tokens.add(new LiteralToken(value));
            } else if (Character.isLetter(character)) {
                int start = i;
                while (i < sizeOfInput && Character.isLetter(rawExpression.charAt(i))) {
                    i++;
                }
                String variable = rawExpression.substring(start, i);
                tokens.add(new VariableToken(variable));
            } else if (isOperator(String.valueOf(character))) {
                tokens.add(new OperatorToken(character));
                i++;
            } else {
                System.out.println("Wrong Input");
                throw new RuntimeException("Please provide valid Input");
            }
        }
        return tokens;
    }

    private List<Token> convertInfixToPostfix(List<Token> tokenizedExpression) {
        List<Token> postfixExpression = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        for (Token token : tokenizedExpression) {
            if (token instanceof OperandToken) {
                postfixExpression.add(token);
            } else if ((token instanceof OperatorToken) && (((OperatorToken) token).getOperator() == '(')) {
                stack.push(token);
            } else if (token instanceof OperatorToken && (((OperatorToken) token).getOperator() == ')')) {
                while (!stack.isEmpty() && stack.peek() instanceof OperatorToken &&
                        !(((OperatorToken) stack.peek()).getOperator() == '(')) {
                    postfixExpression.add(stack.pop());
                }
                stack.pop();
            } else if (token instanceof OperatorToken) {
                while (!stack.isEmpty() && stack.peek() instanceof OperatorToken &&
                        getPrecedence(((OperatorToken) stack.peek()).getOperator()) >=
                                getPrecedence((char) ((OperatorToken) token).getOperator())) {
                    postfixExpression.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            Token top = stack.pop();
            postfixExpression.add(top);
        }
        return postfixExpression;
    }

    private int getPrecedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    private boolean isOperator(String token) {
        char operator = token.charAt(0);
        return operator == '+' || operator == '-' || operator == '*'
                || operator == '/' || operator == '^' || operator == '(' || operator == ')';
    }

}
