package com.cognitree.internship.exp_eval;

public class OperatorToken implements Token {
    private final char operator;

    public OperatorToken(char operator) {
        this.operator = operator;
    }

    public int getPrecedence() {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    @Override
    public Object getValue() {
        return operator;
    }
}
