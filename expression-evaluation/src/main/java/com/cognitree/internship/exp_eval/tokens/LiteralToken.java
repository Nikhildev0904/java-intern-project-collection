package com.cognitree.internship.exp_eval.tokens;

public class LiteralToken implements OperandToken {
    private final double value;

    public LiteralToken(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LiteralToken{" +
                "value=" + value +
                '}';
    }
}
