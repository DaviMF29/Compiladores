package com.uepb.parser.ast;

public class VariableNode extends ExprNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {
        return 0.0;
    }

    @Override
    public String toString() {
        return name; 
    }
}
