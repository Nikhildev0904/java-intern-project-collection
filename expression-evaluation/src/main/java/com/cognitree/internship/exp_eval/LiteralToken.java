package com.cognitree.internship.exp_eval;

public class LiteralToken implements OperandToken {
    private final double value;

    public LiteralToken(double value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
