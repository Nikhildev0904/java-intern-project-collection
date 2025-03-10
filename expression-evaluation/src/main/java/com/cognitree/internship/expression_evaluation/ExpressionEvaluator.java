package com.cognitree.internship.expression_evaluation;

import java.util.*;

public class ExpressionEvaluator {
    private final ExpressionParser expressionParser;
    private List<String> parsedExpression;

    public ExpressionEvaluator() {
        this.expressionParser = new ExpressionParser();
    }

    public void processingInput(String input) {
        List<String> expression = expressionParser.tokenise(input);
        if (expression != null) {
            this.parsedExpression = expressionParser.infixToPostfix(expression);
        }
    }

    public double evaluate() {
        HashMap<String, Double> userValues = enterVariables();
        double result = expressionParser.evaluateExpression(userValues, parsedExpression);
        return result;
    }

    public HashMap<String, Double> enterVariables() {
        Scanner scanner = new Scanner(System.in);
        Set<String> variables = expressionParser.extractVariables(parsedExpression);
        HashMap<String, Double> valuesOfVariables = new HashMap<>();
        for (String variable : variables) {
            System.out.print("Enter the value of " + variable + ": ");
            double value = Double.parseDouble(scanner.nextLine());
            valuesOfVariables.put(variable, value);
        }
        return valuesOfVariables;
    }

}