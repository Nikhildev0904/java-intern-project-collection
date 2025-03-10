package com.cognitree.internship.exp_eval;

import java.util.*;

public class ExpressionEvaluator {
    private final ExpressionParser expressionParser;
    private List<String> parsedExpression;

    public ExpressionEvaluator() {
        this.expressionParser = new ExpressionParser();
    }

    public boolean tokenizeAndConvertToPostfix(String expression) {
        List<String> parsedExpression = expressionParser.parsingExpression(expression);
        if (parsedExpression != null) {
            this.parsedExpression = parsedExpression;
            return true;
        }
        return false;
    }

    public double evaluate(HashMap<String, Double> userValues) {
        double result = expressionParser.evaluateExpression(userValues, parsedExpression);
        return result;
    }

    public Set<String> extractVariables() throws RuntimeException {
        Set<String> variables = expressionParser.extractVariables(parsedExpression);
        return variables;
    }

}