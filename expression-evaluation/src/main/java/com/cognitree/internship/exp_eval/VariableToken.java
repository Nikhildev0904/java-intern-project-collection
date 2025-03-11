package com.cognitree.internship.exp_eval;

public class VariableToken implements OperandToken {
    private final String varName;

    public VariableToken(String varName) {
        this.varName = varName;
    }

    @Override
    public Object getValue() {
        return varName;
    }
}
