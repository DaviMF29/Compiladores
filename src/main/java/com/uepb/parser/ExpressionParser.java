package com.uepb.parser;

import java.io.IOException;

import com.uepb.parser.ast.BinaryOpNode;
import com.uepb.parser.ast.ExprNode;
import com.uepb.parser.ast.NumberNode;
import com.uepb.parser.ast.VariableNode;
import com.uepb.parser.exceptions.SyntaxError;
import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class ExpressionParser {

    private final TokenBuffer tokenBuffer;

    public ExpressionParser(TokenBuffer tokenBuffer) throws IOException {
        this.tokenBuffer = tokenBuffer;
    }

    public ExprNode parse() throws IOException, SyntaxError {
        ExprNode lastResult = null;
    
        while (true) {
            Token lookahead = tokenBuffer.lookAhead(1);
            if (lookahead.type() == TokenType.EOF) {
                break; 
            }
    
            lastResult = expression(); 
    
            double currentValue = lastResult.evaluate();
    
            lookahead = tokenBuffer.lookAhead(1);
            if (lookahead.type() == TokenType.SEMICOLON) {
                tokenBuffer.match(TokenType.SEMICOLON); 
            } else {
                throw new SyntaxError("Esperado ';' após a expressão");
            }
            
            //poderia adicionar uma verificação para imprimir <var, operator, number>

            System.out.println("Expressão avaliada: " + lastResult + ", Valor: " + currentValue);
        }
    
        return lastResult;
    }
    
    
    private ExprNode expression() throws IOException, SyntaxError {
        ExprNode left = exponent();
        while (isAddOp(tokenBuffer.lookAhead(1))) {
            Token operator = tokenBuffer.lookAhead(1);
            tokenBuffer.match(operator.type());
            ExprNode right = exponent(); 
            left = new BinaryOpNode(left, operator, right);
        }
        return left;
    }

    private ExprNode exponent() throws IOException, SyntaxError {
        ExprNode left = term();
        while (tokenBuffer.lookAhead(1).type() == TokenType.POWER) {
            Token operator = tokenBuffer.lookAhead(1);
            tokenBuffer.match(operator.type());
            ExprNode right = exponent(); 
            left = new BinaryOpNode(left, operator, right);
        }
        return left;
    }

    private ExprNode term() throws IOException, SyntaxError {
        ExprNode left = factor();
        while (isMulOp(tokenBuffer.lookAhead(1))) {
            Token operator = tokenBuffer.lookAhead(1);
            tokenBuffer.match(operator.type());
            ExprNode right = factor();
            left = new BinaryOpNode(left, operator, right);
        }
        return left;
    }

    private ExprNode factor() throws IOException, SyntaxError {
        Token lookahead = tokenBuffer.lookAhead(1);

        if (lookahead.type() == TokenType.MINUS) {
            tokenBuffer.match(TokenType.MINUS);
            return new NumberNode(-factor().evaluate());
        } else if (lookahead.type() == TokenType.NUMBER) {
            tokenBuffer.match(TokenType.NUMBER);
            return new NumberNode(Double.parseDouble(lookahead.lexema()));
        } else if (lookahead.type() == TokenType.VAR) {
            tokenBuffer.match(TokenType.VAR);
            String variableName = lookahead.lexema();
            return new VariableNode(variableName);
        } else if (lookahead.type() == TokenType.LPAREN) {
            tokenBuffer.match(TokenType.LPAREN);
            ExprNode result = expression();
            tokenBuffer.match(TokenType.RPAREN);
            return result;
        } else {
            throw new SyntaxError(lookahead);
        }
    }

    private boolean isAddOp(Token token) {
        return token.type() == TokenType.PLUS || token.type() == TokenType.MINUS;
    }

    private boolean isMulOp(Token token) {
        return token.type() == TokenType.MULTIPLY || token.type() == TokenType.DIVIDE;
    }
}