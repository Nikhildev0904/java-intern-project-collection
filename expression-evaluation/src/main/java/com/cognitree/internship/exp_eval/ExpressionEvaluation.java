package com.cognitree.internship.exp_eval;

import java.util.*;

public class ExpressionEvaluation {
    private List<String> parsedExpression;

    public Set<String> extractVariables(String expression) {
        ExpressionParser expressionParser = new ExpressionParser();
        this.parsedExpression = expressionParser.parsingExpression(expression);
        Set<String> extractedVariables = expressionParser.extractVariables(parsedExpression);
        return extractedVariables;
    }

    public double evaluate(HashMap<String, Double> variableValues) {
        ExpressionCalculator expressionCalculator = new ExpressionCalculator();
        double result = expressionCalculator.calculate(variableValues, this.parsedExpression);
        return result;
    }


}
