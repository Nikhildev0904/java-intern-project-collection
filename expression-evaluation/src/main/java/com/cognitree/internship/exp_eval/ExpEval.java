package com.cognitree.internship.exp_eval;

import com.cognitree.internship.exp_eval.tokens.LiteralToken;
import com.cognitree.internship.exp_eval.tokens.OperatorToken;
import com.cognitree.internship.exp_eval.tokens.Token;
import com.cognitree.internship.exp_eval.tokens.VariableToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ExpEval {

    private static final Logger logger = LoggerFactory.getLogger(ExpEval.class);

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
        logger.debug("Evaluating parsed expression: {}", parsedExpression);
        for (Token token : parsedExpression) {
            if (token instanceof LiteralToken literalToken) {
                stack.push(literalToken.getValue());
            } else if (token instanceof OperatorToken operatorToken) {
                if (stack.size() < 2) {
                    throw new RuntimeException("Insufficient operands for operator " + operatorToken.getOperator());
                }
                double value2 = stack.pop();
                double value1 = stack.pop();
                double result = applyOperation(value1, value2, (operatorToken.getOperator()));
                stack.push(result);
            } else {
                if (token instanceof VariableToken variableToken) {
                    Double value = variables.get(variableToken.getVarName());
                    if (value == null) {
                        throw new RuntimeException("Undefined variable used: " + variableToken.getVarName());
                    }
                    stack.push(value);
                }
            }
        }
        logger.info("Expression evaluation completed");
        return stack.pop();
    }

    private double applyOperation(double value1, double value2, char operator) {
        return switch (operator) {
            case '+' -> value1 + value2;
            case '-' -> value1 - value2;
            case '*' -> value1 * value2;
            case '/' -> value1 / value2;
            case '^' -> Math.pow(value1, value2);
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }
}