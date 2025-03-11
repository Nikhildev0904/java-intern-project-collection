package com.cognitree.internship.exp_eval.tokens;

public class VariableToken implements OperandToken {
    private final String varName;

    public VariableToken(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }
}
