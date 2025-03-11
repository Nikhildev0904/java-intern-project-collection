package com.cognitree.internship.exp_eval;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ExpEval {
    private final ExpParser expressionParser;

    public ExpEval(String expression) {
        this.expressionParser = new ExpParser(expression);
    }

    public Set<String> getVariables() {
        return expressionParser.extractVariables();
    }

    public double calculate(Map<String, Double> variables) {
        Stack<Double> stack = new Stack<>();
        List<Token> parsedExpression = expressionParser.getParsedExpression();
        for (Token token : parsedExpression) {
            if (token instanceof LiteralToken) {
                stack.push((double) token.getValue());
            } else if (token instanceof OperatorToken) {
                if (stack.size() < 2) {
                    throw new RuntimeException("Insufficient Operands");
                }
                double value2 = stack.pop();
                double value1 = stack.pop();
                double result = applyOperation(value1, value2, (char) token.getValue());
                stack.push(result);
            } else {
                stack.push(variables.get((String) token.getValue()));
            }
        }
        return stack.pop();
    }

    private double applyOperation(double value1, double value2, char operator) {
        switch (operator) {
            case '+':
                return value1 + value2;
            case '-':
                return value1 - value2;
            case '*':
                return value1 * value2;
            case '/':
                return value1 / value2;
            case '^':
                return Math.pow(value1, value2);
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
}