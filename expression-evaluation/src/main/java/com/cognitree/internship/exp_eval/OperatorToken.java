package com.cognitree.internship.exp_eval;

public class OperatorToken implements Token {
    private final char operator;

    public OperatorToken(char operator) {
        this.operator = operator;
    }

    @Override
    public Object getValue() {
        return operator;
    }
}
