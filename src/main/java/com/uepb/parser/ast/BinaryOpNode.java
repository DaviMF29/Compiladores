package com.uepb.parser.ast;

import com.uepb.token.Token;

public class BinaryOpNode extends ExprNode {
    private final ExprNode left; 
    private final Token operator;
    private final ExprNode right;

    public BinaryOpNode(ExprNode left, Token operator, ExprNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public double evaluate() {
        double leftValue = left.evaluate();
        double rightValue = right.evaluate();

        switch (operator.type()) {
            case PLUS:
                return leftValue + rightValue;
            case MINUS:
                return leftValue - rightValue;
            case MULTIPLY:
                return leftValue * rightValue;
            case POWER:
                return Math.pow(leftValue, rightValue);
            case DIVIDE:
                if (rightValue == 0) {
                    throw new RuntimeException("Divisão por zero.");
                }
                return leftValue / rightValue;
            default:
                throw new RuntimeException("Operador desconhecido: " + operator);
        }
    }

    @Override
    public String toString() {
        return "<" + left + ", " + operator.lexema() + " , " + right + ">";
    }
}
