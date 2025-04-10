package com.cognitree.internship.exp_eval.tokens;

public class OperatorToken implements Token {
    private final char operator;

    public OperatorToken(char operator) {
        this.operator = operator;
    }

    public char getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "OperatorToken{" +
                "operator=" + operator +
                '}';
    }
}
