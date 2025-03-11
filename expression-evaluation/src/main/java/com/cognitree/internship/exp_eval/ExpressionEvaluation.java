package com.cognitree.internship.exp_eval;

import java.util.Set;
import java.util.Map;
import java.util.List;

public class ExpressionEvaluation {
    private final ExpressionParser expressionParser;
    private final ExpressionCalculator expressionCalculator;
    private final List<String> parsedExpression;

    public ExpressionEvaluation(String expression) {
        this.expressionParser = new ExpressionParser();
        this.expressionCalculator = new ExpressionCalculator();
        this.parsedExpression = expressionParser.parsingExpression(expression);
    }

    public Set<String> getVariables() {
        Set<String> extractedVariables = expressionParser.extractVariables(this.parsedExpression);
        return extractedVariables;
    }

    public double evaluate(Map<String, Double> variableValues) {
        double result = expressionCalculator.calculate(variableValues, this.parsedExpression);
        return result;
    }
}
